package com.michel.dao;


import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Client;
import com.michel.utilitaires.LoggerReverso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

public class DaoClient {

    public static ArrayList findAllClient() throws SQLException, MetierException {

        String query = "SELECT societe.ID_SOCIETE AS 'identifiant', " +
                "NOM_SOCIETE AS 'raisonSociale', " +
                "NUM_ADRESSE AS 'numero', " +
                "RUE_ADRESSE AS 'nomRue', " +
                "NUM_CP AS 'codePostal', " +
                "NOM_VILLE AS 'ville', " +
                "TEL_SOCIETE AS 'telephone', " +
                "MAIL_SOCIETE AS 'email', " +
                "COM_SOCIETE AS 'commentaire', " +
                "CA_CLIENT AS 'chiffreAffaire', " +
                "NBRE_EMPLOYE AS 'nbreEmploye' " +
                "FROM societe " +
                "INNER JOIN client on client.ID_SOCIETE = societe.ID_SOCIETE " +
                "INNER JOIN adresse on adresse.ID_ADRESSE = societe.ID_ADRESSE " +
                "INNER JOIN code_postal on code_postal.ID_CP = adresse.ID_CP " +
                "INNER JOIN ville on ville.ID_VILLE = code_postal.ID_VILLE;";
        ArrayList<Client> clients = new ArrayList<>();

        try (Statement stmt = DaoConnection.getInstance().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //Création objet Adresse
                Adresse adresse = new Adresse(rs.getString("numero"),
                    rs.getString("nomRue"),
                    rs.getString("ville"),
                    rs.getInt("codePostal"));
                //Creation Objet client
                Client client = new Client(rs.getInt("identifiant"),
                    rs.getString("raisonSociale"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("commentaire"),
                    adresse,
                    rs.getDouble("chiffreAffaire"),
                    rs.getInt("nbreEmploye"));
                //Insertion Client dans ArraysList
                clients.add(client);
            }
        }catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    e.getMessage() + " " + e);
        }
        return clients;
    }

    public static Client findByNameClient(String raisonSociale) throws SQLException, MetierException {

        Client client = new Client();

        String query = "SELECT societe.ID_SOCIETE AS 'identifiant', " +
                "NUM_ADRESSE AS 'numero', " +
                "RUE_ADRESSE AS 'nomRue', " +
                "NUM_CP AS 'codePostal', " +
                "NOM_VILLE AS 'ville', " +
                "TEL_SOCIETE AS 'telephone', " +
                "MAIL_SOCIETE AS 'email', " +
                "COM_SOCIETE AS 'commentaire', " +
                "CA_CLIENT AS 'chiffreAffaire', " +
                "NBRE_EMPLOYE AS 'nbreEmploye' " +
                "FROM societe " +
                "INNER JOIN client on client.ID_SOCIETE = societe.ID_SOCIETE " +
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
                //Creation Objet client
                client = new Client(rs.getInt("identifiant"),
                        raisonSociale,
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("commentaire"),
                        adresse,
                        rs.getDouble("chiffreAffaire"),
                        rs.getInt("nbreEmploye"));
            }
        } catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    e.getMessage() + " " + e);
        }
        return client;
    }

    public static void createClient (Client client) throws SQLException, DaoException {

        String queryIdClient = "SELECT ID_CLIENT FROM client " +
                "INNER JOIN societe on client.ID_SOCIETE = societe.ID_SOCIETE " +
                "WHERE NOM_SOCIETE LIKE '" + client.getRaisonSociale() + "';";
        // Vérifier si la id_client n'est pas existant dans la table client
        try (Statement stmt = DaoConnection.getInstance().createStatement()) {
            ResultSet rsIdClient = stmt.executeQuery(queryIdClient);
            //si inexistante
            if(!rsIdClient.next()){
                int idSociete = 0;
                //recherche idAdresse et insertion Adresse si inexistante
                int idAdresse = DaoAdresse.creerAdresse(client.getAdresse());
                //verfication si raison sociale existe
                String queryRS = "SELECT ID_SOCIETE, NOM_SOCIETE FROM societe " +
                        "WHERE NOM_SOCIETE LIKE '" + client.getRaisonSociale() + "'";
                ResultSet rsRaisonSociale = stmt.executeQuery(queryRS);
                if (rsRaisonSociale.next()){
                    while (rsRaisonSociale.next()){
                        idSociete = rsRaisonSociale.getInt(1);
                    }
                    stmt.execute("UPDATE societe SET NOM_SOCIETE ='" + client.getRaisonSociale() + "'," +
                            "ID_ADRESSE='" + idAdresse + "'," +
                            "TEL_SOCIETE='" + client.getTelephone() + "'," +
                            "MAIL_SOCIETE='" + client.getEmail() + "'," +
                            "COM_SOCIETE='" + client.getCommentaire() + "' WHERE ID_SOCIETE = " + idSociete + ";");
                } else {
                    //insertion nouvelle societe dans la table societe si raison sociale inexistante
                    stmt.execute("INSERT INTO `societe` (`ID_SOCIETE`, `NOM_SOCIETE`, `ID_ADRESSE`, " +
                            "`TEL_SOCIETE`, `MAIL_SOCIETE`, `COM_SOCIETE`) " +
                            "VALUES (NULL, '" + client.getRaisonSociale() + "', " +
                            "'" + idAdresse + "', " +
                            "'" + client.getTelephone() + "', " +
                            "'" + client.getEmail() + "', " +
                            "'" + client.getCommentaire() + "');", Statement.RETURN_GENERATED_KEYS);
                    //retour clé primaire
                    ResultSet rsIdSociete = stmt.getGeneratedKeys();
                    while (rsIdSociete.next()) {
                        idSociete = rsIdSociete.getInt(1);
                    }
                }
                //insertion dans la table client
                stmt.execute("INSERT INTO `client` (`ID_CLIENT`, `ID_SOCIETE`, `CA_CLIENT`, `NBRE_EMPLOYE`) " +
                        "VALUES (NULL, '" + idSociete + "', " +
                        "'" + client.getChiffreAffaire() + "', " +
                        "'" + client.getNbreEmploye() + "');");
            // si dèjà existante retour message utilisateur
            } else {
                throw new DaoException("Cette entreprise est cliente");
            }
        } catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                e.getMessage() + " " + e);
        }
    }

    public static void updateCLient (Client client, int idSociete) throws SQLException, DaoException {

        //recherche idAdresse et insertion Adresse si inexistante
        int idAdresse = DaoAdresse.creerAdresse(client.getAdresse());
        //verification nouvelle raison sociale n'existe pas dans la base de données avant modification
        String queryRS = "SELECT NOM_SOCIETE FROM societe WHERE NOM_SOCIETE LIKE '" + client.getRaisonSociale() + "' " +
                "AND ID_SOCIETE != " + idSociete + ";";
        try (Statement stmt = DaoConnection.getInstance().createStatement()) {
            ResultSet rsRaisonSociale = stmt.executeQuery(queryRS);
            if(rsRaisonSociale.next()){
                throw new DaoException("Raison sociale déjà existante");
            }
            stmt.execute("UPDATE societe SET NOM_SOCIETE ='" + client.getRaisonSociale() + "'," +
                    "ID_ADRESSE='" + idAdresse + "'," +
                    "TEL_SOCIETE='" + client.getTelephone() + "'," +
                    "MAIL_SOCIETE='" + client.getEmail() + "'," +
                    "COM_SOCIETE='" + client.getCommentaire() + "' WHERE ID_SOCIETE = " + idSociete + ";");
            stmt.execute("UPDATE client SET CA_CLIENT = '" + client.getChiffreAffaire() + "', " +
                    "NBRE_EMPLOYE = '" + client.getNbreEmploye() + "' " +
                    "WHERE ID_SOCIETE = " + idSociete + ";");
        } catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    e.getMessage() + " " + e);
        }
    }

    public static void deleteClient (int idSociete) throws SQLException {

        try(Statement stmt = DaoConnection.getInstance().createStatement()){
            //supprimer dans la table client
            stmt.execute("DELETE FROM client WHERE ID_SOCIETE = " + idSociete + ";");
            String queryProspect = "SELECT ID_SOCIETE FROM prospect WHERE ID_SOCIETE = " + idSociete + ";";
            ResultSet rsProspect = stmt.executeQuery(queryProspect);
            // si la societe n'est pas rattaché au prospect on supprime la socièté
            if (!rsProspect.next()){
                stmt.execute("DELETE FROM societe WHERE ID_SOCIETE = " + idSociete + ";");
            }
        } catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    e.getMessage() + " " + e);
        }
    }
}