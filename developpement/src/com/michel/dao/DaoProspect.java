package com.michel.dao;

import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Prospect;
import com.michel.utilitaires.LoggerReverso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.michel.dao.DaoConnection.getInstance;

/**
 * création modification suppression et recherche dans la table prospect de la BDD
 */
public class DaoProspect {

    private static final String MESSAGE_ERREUR ="problème lecture BDD";
    private static final String MESSAGE_FERMETURE = "problème connection base de donnée, le logiciel va fermer" ;
    private static final String MESSAGE_METIER = "problème métier dans la base de données";

    private DaoProspect(){}

    /**
     *
     */
    /**
     * findAll
     * @return ArrayList Object Prospect
     * @throws MetierException propagation
     * @throws DaoException si pb avec la BDD
     * @throws SQLException
     */
    public static List<Prospect> findAll() throws DaoException, SQLException {
        PreparedStatement pstmt = null;

        Connection connection = getInstance();


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

        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //Création objet Adresse
                Adresse adresse = new Adresse(rs.getString("numero"),
                        rs.getString("nomRue"),
                        rs.getString("ville"),
                        rs.getString("codePostal"));
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
        } catch (MetierException e){
            StringBuilder messageLog = new StringBuilder(MESSAGE_METIER);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Erreur base de donnée, le logiciel va fermer");
        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(MESSAGE_ERREUR);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, MESSAGE_FERMETURE);
        } finally {
            if (pstmt != null){
                pstmt.close();
            }
        }
        return prospects;
    }

    /**
     * findByName
     * @param raisonSociale String ne doit pas etre null
     * @return Object Propsect
     * @throws MetierException propagation
     * @throws DaoException si pb avec la BDD
     */
    public static Prospect findByName(String raisonSociale) throws DaoException, SQLException {

        if (raisonSociale == null || raisonSociale.trim().isEmpty()){
            throw new DaoException(1, "attention la raison Sociale est vide");
        }

        PreparedStatement pstmtRS = null;
        Prospect prospect = new Prospect();

        Connection connection = getInstance();

        String queryRS = "SELECT societe.ID_SOCIETE AS 'identifiant', " +
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
                "WHERE NOM_SOCIETE LIKE ?;";

        try {
            pstmtRS = connection.prepareStatement(queryRS);
            pstmtRS.setString(1, raisonSociale);
            ResultSet rs = pstmtRS.executeQuery();
            while (rs.next()) {
                Adresse adresse = new Adresse(rs.getString("numero"),
                        rs.getString("nomRue"),
                        rs.getString("ville"),
                        rs.getString("codePostal"));
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
        } catch (MetierException e){
            StringBuilder messageLog = new StringBuilder(MESSAGE_METIER);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Erreur base de donnée, le logiciel va fermer");
        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(MESSAGE_ERREUR);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, MESSAGE_FERMETURE);
        } finally {
            if (pstmtRS != null){
                pstmtRS.close();
            }
        }
        return prospect;
    }

    /**
     * create
     * @param prospect Object Prospect
     * @throws DaoException si pb avec la BDD
     */
    public static void create(Prospect prospect) throws DaoException, SQLException {

        Connection connection = getInstance();

        String queryIdProspect = "SELECT ID_PROSPECT FROM prospect " +
                "INNER JOIN societe on prospect.ID_SOCIETE = societe.ID_SOCIETE " +
                "WHERE NOM_SOCIETE LIKE ?;";
        String queryRS = "SELECT ID_SOCIETE, NOM_SOCIETE FROM societe " +
                "WHERE NOM_SOCIETE LIKE ?";
        String queryUpdateSociete = "UPDATE societe SET NOM_SOCIETE = ?," +
                "ID_ADRESSE= ?," +
                "TEL_SOCIETE= ?," +
                "MAIL_SOCIETE= ?," +
                "COM_SOCIETE= ? WHERE ID_SOCIETE = ?;";
        String queryInsertSociete = "INSERT INTO `societe` (`ID_SOCIETE`, `NOM_SOCIETE`, `ID_ADRESSE`, " +
                "`TEL_SOCIETE`, `MAIL_SOCIETE`, `COM_SOCIETE`) " +
                "VALUES (NULL, ?, ?, ?, ?, ?);";
        String queryInseretProspect = "INSERT INTO `prospect` (`ID_PROSPECT`, `ID_SOCIETE`, `DATE_PROSPECT`, `INTERET_PROSPECT`) " +
                "VALUES (NULL, ?, ?, ?);";

        // Vérifier si la id_prospect n'est pas existant dans la table client
        try (PreparedStatement pstmtIdProspect = connection.prepareStatement(queryIdProspect);
             PreparedStatement pstmtRS = connection.prepareStatement(queryRS);
             PreparedStatement pstmtUpdateSociete = connection.prepareStatement(queryUpdateSociete);
             PreparedStatement pstmtInsertSociete = connection.prepareStatement(queryInsertSociete, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtInsertProspect = connection.prepareStatement(queryInseretProspect)) {

            pstmtIdProspect.setString(1, prospect.getRaisonSociale());
            ResultSet rsIdClient = pstmtIdProspect.executeQuery();
            //si inexistante
            if(!rsIdClient.next()){
                int idSociete = 0;
                //recherche idAdresse et insertion Adresse si inexistante
                int idAdresse = DaoAdresse.creerAdresse(prospect.getAdresse());
                //verfication si raison sociale existe
                pstmtRS.setString(1, prospect.getRaisonSociale());
                ResultSet rsRaisonSociale = pstmtRS.executeQuery();
                if (rsRaisonSociale.next()){
                    while (rsRaisonSociale.next()){
                        idSociete = rsRaisonSociale.getInt(1);
                    }
                    pstmtUpdateSociete.setString(1, prospect.getRaisonSociale());
                    pstmtUpdateSociete.setInt(2, idAdresse);
                    pstmtUpdateSociete.setString(3, prospect.getTelephone());
                    pstmtUpdateSociete.setString(4, prospect.getEmail());
                    pstmtUpdateSociete.setString(5, prospect.getCommentaire());
                    pstmtUpdateSociete.setInt(6, idSociete);
                    pstmtUpdateSociete.executeUpdate();
                } else {
                    //insertion nouvelle societe dans la table societe si raison sociale inexistante
                    pstmtInsertSociete.setString(1, prospect.getRaisonSociale());
                    pstmtInsertSociete.setInt(2, idAdresse);
                    pstmtInsertSociete.setString(3, prospect.getTelephone());
                    pstmtInsertSociete.setString(4, prospect.getEmail());
                    pstmtInsertSociete.setString(5, prospect.getCommentaire());
                    pstmtInsertSociete.executeUpdate();
                    //retour clé primaire
                    ResultSet rsIdSociete = pstmtInsertSociete.getGeneratedKeys();
                    if (rsIdSociete.next()) {
                        idSociete = rsIdSociete.getInt(1);
                    }
                }
                Date date = Date.valueOf(prospect.getDateProspect());
                //insertion dans la table prospect
                pstmtInsertProspect.setInt(1, idSociete);
                pstmtInsertProspect.setDate(2, date);
                pstmtInsertProspect.setInt(3, prospect.getInteretProspect());
                pstmtInsertProspect.executeUpdate();
                // si dèjà existante retour message utilisateur
            } else {
                throw new DaoException(1, "Cette entreprise est prospecte");
            }
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity){
            if (sqlIntegrity.getErrorCode() == 1062){
                throw new DaoException(1, "Attention cette socièté existe");
            }
            if (sqlIntegrity.getErrorCode() == 1406) {
                throw new DaoException(1, "Un paramètre n'est pas bon");
            }
        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(MESSAGE_ERREUR);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, MESSAGE_FERMETURE);
        }
    }

    /**
     * update
     * @param prospect Object Prospect
     * @throws DaoException si pb avec la BDD
     */
    public static void update(Prospect prospect) throws DaoException, SQLException {
        int idSociete = prospect.getIdentifiant();

        Connection connection = getInstance();

        String queryRS = "SELECT NOM_SOCIETE FROM societe WHERE NOM_SOCIETE LIKE ? AND ID_SOCIETE != ?";
        String queryUpdateSociete = "UPDATE societe SET NOM_SOCIETE = ?, ID_ADRESSE = ?, TEL_SOCIETE = ?, MAIL_SOCIETE = ?, COM_SOCIETE = ? WHERE ID_SOCIETE = ?";
        String queryUpdateProspect = "UPDATE prospect SET DATE_PROSPECT = ?, INTERET_PROSPECT = ? WHERE ID_SOCIETE = ?";

        try (PreparedStatement pstmtRS = connection.prepareStatement(queryRS);
            PreparedStatement pstmtUpdateSociete = connection.prepareStatement(queryUpdateSociete);
            PreparedStatement pstmtUpdateProspect = connection.prepareStatement(queryUpdateProspect)) {

            // Recherche idAdresse et insertion Adresse si inexistante
            int idAdresse = DaoAdresse.creerAdresse(prospect.getAdresse());
            // Vérification nouvelle raison sociale n'existe pas dans la base de données avant modification
            pstmtRS.setString(1, prospect.getRaisonSociale());
            pstmtRS.setInt(2, idSociete);
            ResultSet rsRaisonSociale = pstmtRS.executeQuery();
            if (rsRaisonSociale.next()) {
                throw new DaoException(1, "Raison sociale déjà existante");
            }
            Date date = Date.valueOf(prospect.getDateProspect());

            pstmtUpdateSociete.setString(1, prospect.getRaisonSociale());
            pstmtUpdateSociete.setInt(2, idAdresse);
            pstmtUpdateSociete.setString(3, prospect.getTelephone());
            pstmtUpdateSociete.setString(4, prospect.getEmail());
            pstmtUpdateSociete.setString(5, prospect.getCommentaire());
            pstmtUpdateSociete.setInt(6, idSociete);
            pstmtUpdateSociete.executeUpdate();

            pstmtUpdateProspect.setDate(1, date);
            pstmtUpdateProspect.setInt(2, prospect.getInteretProspect());
            pstmtUpdateProspect.setInt(3, idSociete);
            pstmtUpdateProspect.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity){
            if (sqlIntegrity.getErrorCode() == 1062){
                throw new DaoException(1, "Attention cette socièté existe");
            }
            if (sqlIntegrity.getErrorCode() == 1406) {
                throw new DaoException(1, "Un paramètre n'est pas bon");
            }
        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(MESSAGE_ERREUR);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, MESSAGE_FERMETURE);
        }
    }

    /**
     * delete
     * @param idSociete ID_SOCIETE de la table societe de la BDD
     * @throws DaoException si pb avec la BDD
     */
    public static void delete(int idSociete) throws DaoException, SQLException {

        Connection connection = getInstance();

        String queryDeleteProspect = "DELETE FROM prospect WHERE ID_SOCIETE = ?";
        String queryCheckClient = "SELECT ID_SOCIETE FROM client WHERE ID_SOCIETE = ?";
        String queryDeleteSociete = "DELETE FROM societe WHERE ID_SOCIETE = ?";

        try (PreparedStatement pstmtDeleteProspect = connection.prepareStatement(queryDeleteProspect);
            PreparedStatement pstmtCheckClient = connection.prepareStatement(queryCheckClient);
            PreparedStatement pstmtDeleteSociete = connection.prepareStatement(queryDeleteSociete)) {
            pstmtDeleteProspect.setInt(1, idSociete);
            pstmtDeleteProspect.executeUpdate();

            pstmtCheckClient.setInt(1, idSociete);
            ResultSet rsClient = pstmtCheckClient.executeQuery();

            // Si la société n'est pas rattachée à la table client, on la supprime
            if (!rsClient.next()) {
                pstmtDeleteSociete.setInt(1, idSociete);
                pstmtDeleteSociete.executeUpdate();
            }

        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(MESSAGE_ERREUR);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, MESSAGE_FERMETURE);
        }
    }
}
