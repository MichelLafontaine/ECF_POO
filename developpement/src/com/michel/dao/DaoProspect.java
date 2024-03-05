package com.michel.dao;

import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Prospect;
import com.michel.utilitaires.LoggerReverso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

public class DaoProspect {

    public static ArrayList findAll() throws MetierException, DaoException {

        String query = "SELECT societe.ID_SOCIETE AS 'identifiant', " +
                "NOM_SOCIETE AS 'raisonSociale', " +
                "NUM_ADRESSE AS 'numero', " +
                "RUE_ADRESSE AS 'nomRue', " +
                "NUM_CP AS 'codePostal', " +
                "NOM_VILLE AS 'ville', " +
                "TEL_SOCIETE AS 'telephone', " +
                "MAIL_SOCIETE AS 'email', " +
                "COM_SOCIETE AS 'commentaire', " +
                "DATE_PROSPECT AS 'dateProspect', " +
                "INTERET_PROSPECT AS 'interetProspect' " +
                "FROM societe " +
                "INNER JOIN prospect on prospect.ID_SOCIETE = societe.ID_SOCIETE " +
                "INNER JOIN adresse on adresse.ID_ADRESSE = societe.ID_ADRESSE " +
                "INNER JOIN code_postal on code_postal.ID_CP = adresse.ID_CP " +
                "INNER JOIN ville on ville.ID_VILLE = code_postal.ID_VILLE;";
        ArrayList<Prospect> prospects = new ArrayList<>();

        try (Statement stmt = DaoConnection.getInstance().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Création objet Adresse
                Adresse adresse = new Adresse(rs.getString("numero"),
                        rs.getString("nomRue"),
                        rs.getString("ville"),
                        rs.getInt("codePostal"));
                //Creation Objet Prospect
                Prospect prospect = new Prospect(rs.getInt("identifiant"),
                        rs.getString("raisonSociale"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("commentaire"),
                        adresse,
                        // convertir Date(BDD) en LocalDate
                        rs.getDate("dateProspect").toLocalDate(),
                        rs.getInt("interetProspect"));
                //Insertion Client dans ArraysList
                prospects.add(prospect);
            }
        }catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    e.getMessage() + " " + e);
            throw new DaoException(2, "problème connection base de donnée, le logiciel va fermer");
        }
        return prospects;
    }

    public static Prospect findByName(String raisonSociale) throws MetierException, DaoException {

        Prospect prospect = new Prospect();

        String query = "SELECT societe.ID_SOCIETE AS 'identifiant', " +
                "NUM_ADRESSE AS 'numero', " +
                "RUE_ADRESSE AS 'nomRue', " +
                "NUM_CP AS 'codePostal', " +
                "NOM_VILLE AS 'ville', " +
                "TEL_SOCIETE AS 'telephone', " +
                "MAIL_SOCIETE AS 'email', " +
                "COM_SOCIETE AS 'commentaire', " +
                "DATE_PROSPECT AS 'dateProspect', " +
                "INTERET_PROSPECT AS 'interetProspect' " +
                "FROM societe " +
                "INNER JOIN prospect on prospect.ID_SOCIETE = societe.ID_SOCIETE " +
                "INNER JOIN adresse on adresse.ID_ADRESSE = societe.ID_ADRESSE " +
                "INNER JOIN code_postal on code_postal.ID_CP = adresse.ID_CP " +
                "INNER JOIN ville on ville.ID_VILLE = code_postal.ID_VILLE " +
                "WHERE NOM_SOCIETE LIKE '" + raisonSociale + "';";

        try (Statement stmt = DaoConnection.getInstance().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Adresse adresse = new Adresse(rs.getString("numero"),
                        rs.getString("nomRue"),
                        rs.getString("ville"),
                        rs.getInt("codePostal"));
                //Creation Objet prospect
                prospect = new Prospect(rs.getInt("identifiant"),
                        raisonSociale,
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("commentaire"),
                        adresse,
                        rs.getDate("dateProspect").toLocalDate(),
                        rs.getInt("interetProspect"));
            }
        } catch (SQLException | DaoException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    e.getMessage() + " " + e);
            throw new DaoException(2, "problème connection base de donnée, le logiciel va fermer");
        }
        return prospect;
    }

    public static void create(Prospect prospect) throws DaoException {

        String queryIdProspect = "SELECT ID_PROSPECT FROM prospect " +
                "INNER JOIN societe on prospect.ID_SOCIETE = societe.ID_SOCIETE " +
                "WHERE NOM_SOCIETE LIKE '" + prospect.getRaisonSociale() + "';";
        // Vérifier si la id_prospect n'est pas existant dans la table client
        try (Statement stmt = DaoConnection.getInstance().createStatement()) {
            ResultSet rsIdClient = stmt.executeQuery(queryIdProspect);
            //si inexistante
            if(!rsIdClient.next()){
                int idSociete = 0;
                //recherche idAdresse et insertion Adresse si inexistante
                int idAdresse = DaoAdresse.creerAdresse(prospect.getAdresse());
                //verfication si raison sociale existe
                String queryRS = "SELECT ID_SOCIETE, NOM_SOCIETE FROM societe " +
                        "WHERE NOM_SOCIETE LIKE '" + prospect.getRaisonSociale() + "'";
                ResultSet rsRaisonSociale = stmt.executeQuery(queryRS);
                if (rsRaisonSociale.next()){
                    while (rsRaisonSociale.next()){
                        idSociete = rsRaisonSociale.getInt(1);
                    }
                    stmt.execute("UPDATE societe SET NOM_SOCIETE ='" + prospect.getRaisonSociale() + "'," +
                            "ID_ADRESSE='" + idAdresse + "'," +
                            "TEL_SOCIETE='" + prospect.getTelephone() + "'," +
                            "MAIL_SOCIETE='" + prospect.getEmail() + "'," +
                            "COM_SOCIETE='" + prospect.getCommentaire() + "' WHERE ID_SOCIETE = " + idSociete + ";");
                } else {
                    //insertion nouvelle societe dans la table societe si raison sociale inexistante
                    stmt.execute("INSERT INTO `societe` (`ID_SOCIETE`, `NOM_SOCIETE`, `ID_ADRESSE`, " +
                            "`TEL_SOCIETE`, `MAIL_SOCIETE`, `COM_SOCIETE`) " +
                            "VALUES (NULL, '" + prospect.getRaisonSociale() + "', " +
                            "'" + idAdresse + "', " +
                            "'" + prospect.getTelephone() + "', " +
                            "'" + prospect.getEmail() + "', " +
                            "'" + prospect.getCommentaire() + "');", Statement.RETURN_GENERATED_KEYS);
                    //retour clé primaire
                    ResultSet rsIdSociete = stmt.getGeneratedKeys();
                    while (rsIdSociete.next()) {
                        idSociete = rsIdSociete.getInt(1);
                    }
                }
                //insertion dans la table prospect
                stmt.execute("INSERT INTO `prospect` (`ID_PROSPECT`, `ID_SOCIETE`, `DATE_PROSPECT`, `INTERET_PROSPECT`) " +
                        "VALUES (NULL, '" + idSociete + "', " +
                        "'" + prospect.getDateProspect() + "', " +
                        "'" + prospect.getInteretProspect() + "');");
                // si dèjà existante retour message utilisateur
            } else {
                throw new DaoException(1, "Cette entreprise est prospecte");
            }
        } catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    e.getMessage() + " " + e);
            throw new DaoException(2, "problème connection base de donnée, le logiciel va fermer");
        }
    }

    public static void update(Prospect prospect, int idSociete) throws DaoException {

        //recherche idAdresse et insertion Adresse si inexistante
        int idAdresse = DaoAdresse.creerAdresse(prospect.getAdresse());
        //verification nouvelle raison sociale n'existe pas dans la base de données avant modification
        String queryRS = "SELECT NOM_SOCIETE FROM societe WHERE NOM_SOCIETE LIKE '" + prospect.getRaisonSociale() +
                "' " + "AND ID_SOCIETE != " + idSociete + ";";
        try (Statement stmt = DaoConnection.getInstance().createStatement()) {
            ResultSet rsRaisonSociale = stmt.executeQuery(queryRS);
            if(rsRaisonSociale.next()){
                throw new DaoException(1, "Raison sociale déjà existante");
            }
            stmt.execute("UPDATE societe SET NOM_SOCIETE ='" + prospect.getRaisonSociale() + "'," +
                    "ID_ADRESSE='" + idAdresse + "'," +
                    "TEL_SOCIETE='" + prospect.getTelephone() + "'," +
                    "MAIL_SOCIETE='" + prospect.getEmail() + "'," +
                    "COM_SOCIETE='" + prospect.getCommentaire() + "' WHERE ID_SOCIETE = " + idSociete + ";");
            stmt.execute("UPDATE prospect SET DATE_PROSPECT = '" + prospect.getDateProspect() + "', " +
                    "INTERET_PROSPECT = '" + prospect.getInteretProspect() + "' " +
                    "WHERE ID_SOCIETE = " + idSociete + ";");
        } catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    e.getMessage() + " " + e);
            throw new DaoException(2, "problème connection base de donnée, le logiciel va fermer");
        }
    }

    public static void delete(int idSociete) throws DaoException {

        try(Statement stmt = DaoConnection.getInstance().createStatement()){
            //supprimer dans la table prospect
            stmt.execute("DELETE FROM prospect WHERE ID_SOCIETE = " + idSociete + ";");
            String queryClient = "SELECT ID_SOCIETE FROM client WHERE ID_SOCIETE = " + idSociete + ";";
            ResultSet rsProspect = stmt.executeQuery(queryClient);
            // si la societe n'est pas rattaché à la table client on supprime la socièté
            if (!rsProspect.next()){
                stmt.execute("DELETE FROM societe WHERE ID_SOCIETE = " + idSociete + ";");
            }
        } catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    e.getMessage() + " " + e);
            throw new DaoException(2, "problème connection base de donnée, le logiciel va fermer");
        }
    }
}
