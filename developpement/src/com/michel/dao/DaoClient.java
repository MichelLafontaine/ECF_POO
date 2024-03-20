package com.michel.dao;


import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Client;
import com.michel.utilitaires.LoggerReverso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.michel.dao.DaoConnection.*;

/**
 * création modification suppression et recherche dans la table client de la BDD
 */
public class DaoClient {

    private static final String ERREUR_MESSAGE ="problème lecture BDD, ";
    private static final String MESSAGE_FERMETURE ="problème de connection avec la base de données, " +
            "le logiciel va fermer";
    private static final String MESSAGE_METIER = "problème métier dans la base de données";

    private DaoClient() {
    }
    /**
     * finAll client
     * @return ArraysList Objet Client
     * @throws MetierException propagation
     * @throws DaoException si pb avec la BDD
     */

    public static List<Client> findAll() throws DaoException, SQLException {
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
                "CA_CLIENT AS 'chiffreAffaire', " +
                "NBRE_EMPLOYE AS 'nbreEmploye' " +
                "FROM societe " +
                "INNER JOIN client on client.ID_SOCIETE = societe.ID_SOCIETE " +
                "INNER JOIN adresse on adresse.ID_ADRESSE = societe.ID_ADRESSE " +
                "INNER JOIN code_postal on code_postal.ID_CP = adresse.ID_CP " +
                "INNER JOIN ville on ville.ID_VILLE = code_postal.ID_VILLE;";
        ArrayList<Client> clients = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //Création objet Adresse
                Adresse adresse = new Adresse(rs.getString("numero"),
                    rs.getString("nomRue"),
                    rs.getString("ville"),
                    rs.getString("codePostal"));
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
        }
        catch (MetierException e){
            StringBuilder messageLog = new StringBuilder(MESSAGE_METIER);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Erreur base de donnée, le logiciel va fermer");
        }
        catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(ERREUR_MESSAGE);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "problème connection base de donnée, le logiciel va fermer");
        } finally {
            if (pstmt != null){
                pstmt.close();
            }

        }
        return clients;
    }

    /**
     * findByName
     * @param raisonSociale String non null
     * @return Objet Client
     * @throws DaoException si pb connetion BDD
     */
    public static Client findByName(String raisonSociale) throws DaoException, SQLException {

        PreparedStatement pstmt = null;
        Connection connection = getInstance();

        if (raisonSociale == null || raisonSociale.trim().isEmpty()){
            throw new DaoException(1, "attention la raison Sociale est vide");
        }
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
                "WHERE NOM_SOCIETE LIKE ?;";

        try  {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, raisonSociale);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Adresse adresse = new Adresse(rs.getString("numero"),
                        rs.getString("nomRue"),
                        rs.getString("ville"),
                        rs.getString("codePostal"));
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
        } catch (MetierException e){
            StringBuilder messageLog = new StringBuilder(MESSAGE_METIER);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Erreur base de donnée, le logiciel va fermer");
        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(ERREUR_MESSAGE);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, MESSAGE_FERMETURE);
        } finally {
            if (pstmt != null){
                pstmt.close();
            }
        }
        return client;
    }

    /**
     * create
     * @param client Object Client
     * @throws DaoException si pb avec la BDD
     */
    public static void create (Client client) throws DaoException, SQLException {

        Connection connection = getInstance();

        String queryIdClient = "SELECT ID_CLIENT FROM client " +
                "INNER JOIN societe on client.ID_SOCIETE = societe.ID_SOCIETE " +
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
        String queryInsertClient = "INSERT INTO `client` (`ID_CLIENT`, `ID_SOCIETE`, `CA_CLIENT`, `NBRE_EMPLOYE`) " +
                "VALUES (NULL, ?, ?, ?);";
        // Vérifier si la id_client n'est pas existant dans la table client
        try (PreparedStatement pstmtIdClient = connection.prepareStatement(queryIdClient);
            PreparedStatement pstmtRS = connection.prepareStatement(queryRS);
            PreparedStatement pstmtUpdateSociete = connection.prepareStatement(queryUpdateSociete);
            PreparedStatement pstmtInsertSociete = connection.prepareStatement(queryInsertSociete, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstmtInsertClient = connection.prepareStatement(queryInsertClient)) {

            pstmtIdClient.setString(1, client.getRaisonSociale());
            ResultSet rsIdClient = pstmtIdClient.executeQuery();
            //si inexistante
            if(!rsIdClient.next()){
                int idSociete = 0;
                //recherche idAdresse et insertion Adresse si inexistante
                int idAdresse = DaoAdresse.creerAdresse(client.getAdresse());
                //verfication si raison sociale existe
                pstmtRS.setString(1, client.getRaisonSociale());
                ResultSet rsRaisonSociale = pstmtRS.executeQuery();
                if (rsRaisonSociale.next()){
                    while (rsRaisonSociale.next()){
                        idSociete = rsRaisonSociale.getInt(1);
                    }
                    pstmtUpdateSociete.setString(1, client.getRaisonSociale());
                    pstmtUpdateSociete.setInt(2, idAdresse);
                    pstmtUpdateSociete.setString(3, client.getTelephone());
                    pstmtUpdateSociete.setString(4, client.getEmail());
                    pstmtUpdateSociete.setString(5, client.getCommentaire());
                    pstmtUpdateSociete.setInt(6, idSociete);
                    pstmtUpdateSociete.executeUpdate();
                } else {
                    //insertion nouvelle societe dans la table societe si raison sociale inexistante
                    pstmtInsertSociete.setString(1, client.getRaisonSociale());
                    pstmtInsertSociete.setInt(2, idAdresse);
                    pstmtInsertSociete.setString(3, client.getTelephone());
                    pstmtInsertSociete.setString(4, client.getEmail());
                    pstmtInsertSociete.setString(5, client.getCommentaire());
                    pstmtInsertSociete.executeUpdate();
                    //retour clé primaire
                    ResultSet rsIdSociete = pstmtInsertSociete.getGeneratedKeys();
                    if (rsIdSociete.next()) {
                        idSociete = rsIdSociete.getInt(1);
                    }
                }
                //insertion dans la table client
                pstmtInsertClient.setInt(1, idSociete);
                pstmtInsertClient.setDouble(2, client.getChiffreAffaire());
                pstmtInsertClient.setInt(3, client.getNbreEmploye());
                pstmtInsertClient.executeUpdate();
            // si dèjà existante retour message utilisateur
            } else {
                throw new DaoException(1, "Cette entreprise est cliente");
            }
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity){
            if (sqlIntegrity.getErrorCode() == 1062){
                throw new DaoException(1, "Attention cette socièté existe");
            }
            if (sqlIntegrity.getErrorCode() == 1406) {
                throw new DaoException(1, "Un paramètre n'est pas bon");
            }
        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(ERREUR_MESSAGE);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, MESSAGE_FERMETURE);
        }
    }

    /**
     * update
     * @param client Objet client
     * @throws DaoException si pb avec la BDD
     */
    public static void update(Client client) throws DaoException, SQLException {
        int idSociete = client.getIdentifiant();

        Connection connection = getInstance();

        String queryRS = "SELECT NOM_SOCIETE FROM societe WHERE NOM_SOCIETE LIKE ? AND ID_SOCIETE != ?";
        String queryUpdateClient = "UPDATE client SET CA_CLIENT=?, NBRE_EMPLOYE=? WHERE ID_SOCIETE = ?";
        String queryUpdateSociete = "UPDATE societe SET NOM_SOCIETE =?, ID_ADRESSE=?, TEL_SOCIETE=?, MAIL_SOCIETE=?, " +
                "COM_SOCIETE=? WHERE ID_SOCIETE = ?";

        try (PreparedStatement pstmtRS = connection.prepareStatement(queryRS);
            PreparedStatement pstmtUpdateClient = connection.prepareStatement(queryUpdateClient);
            PreparedStatement pstmtUpdateSociete = connection.prepareStatement(queryUpdateSociete)) {
            // Recherche idAdresse et insertion Adresse si inexistante
            int idAdresse = DaoAdresse.creerAdresse(client.getAdresse());
            // Vérification nouvelle raison sociale n'existe pas dans la base de données avant modification
            pstmtRS.setString(1, client.getRaisonSociale());
            pstmtRS.setInt(2, idSociete);
            ResultSet rsRaisonSociale = pstmtRS.executeQuery();
            if (rsRaisonSociale.next()) {
                throw new DaoException(1, "Raison sociale déjà existante");
            }
            pstmtUpdateSociete.setString(1, client.getRaisonSociale());
            pstmtUpdateSociete.setInt(2, idAdresse);
            pstmtUpdateSociete.setString(3, client.getTelephone());
            pstmtUpdateSociete.setString(4, client.getEmail());
            pstmtUpdateSociete.setString(5, client.getCommentaire());
            pstmtUpdateSociete.setInt(6, idSociete);
            pstmtUpdateSociete.executeUpdate();

            pstmtUpdateClient.setDouble(1, client.getChiffreAffaire());
            pstmtUpdateClient.setInt(2, client.getNbreEmploye());
            pstmtUpdateClient.setInt(3, idSociete);
            pstmtUpdateClient.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException sqlIntegrity){
            if (sqlIntegrity.getErrorCode() == 1062){
                throw new DaoException(1, "Attention cette socièté existe");
            }
            if (sqlIntegrity.getErrorCode() == 1406) {
                throw new DaoException(1, "Un paramètre n'est pas bon");
            }
        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(ERREUR_MESSAGE);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, MESSAGE_FERMETURE);
        }
    }

    /**
     * deleteClient
     * @param idSociete ID_SOCIETE de la table societe de la BDD
     * @throws DaoException si pb avec la BDD
     */
    public static void deleteClient (int idSociete) throws DaoException, SQLException {

        Connection connection =getInstance();

        String deleteClientQuery = "DELETE FROM client WHERE ID_SOCIETE = ?";
        String selectProspectQuery = "SELECT ID_SOCIETE FROM prospect WHERE ID_SOCIETE = ?";
        String deleteSocieteQuery = "DELETE FROM societe WHERE ID_SOCIETE = ?";

        try (PreparedStatement pstmtDeleteClient = connection.prepareStatement(deleteClientQuery);
            PreparedStatement pstmtSelectProspect = connection.prepareStatement(selectProspectQuery);
            PreparedStatement pstmtDeleteSociete = connection.prepareStatement(deleteSocieteQuery)){
            // Supprimer dans la table client
            pstmtDeleteClient.setInt(1, idSociete);
            pstmtDeleteClient.executeUpdate();

            // Vérifier si la société est liée à un prospect
            pstmtSelectProspect.setInt(1, idSociete);
            ResultSet rsProspect = pstmtSelectProspect.executeQuery();

            // Si la société n'est pas rattachée au prospect, on supprime la société
            if (!rsProspect.next()) {
                pstmtDeleteSociete.setInt(1, idSociete);
                pstmtDeleteSociete.executeUpdate();
            }
        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder(ERREUR_MESSAGE);
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Problème de connexion avec la base de données, le logiciel va fermer");
        }
    }
}