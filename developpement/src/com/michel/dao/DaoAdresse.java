package com.michel.dao;

import com.michel.exceptions.DaoException;
import com.michel.metiers.Adresse;
import com.michel.utilitaires.LoggerReverso;

import java.sql.*;
import java.util.logging.Level;

/**
 * Vérification/création d'adresse dans la BDD
 */
public class DaoAdresse {

    private DaoAdresse(){}
    /**
     * creerAdresse
     * @param adresse Object Adresse
     * @return idAdresse de la table adresse
     * @throws DaoException si probleme avec la BDD
     */

    public static int creerAdresse(Adresse adresse) throws DaoException, SQLException {
        int idAdresse = 0;
        int idVille = 0;
        int idCP = 0;
        PreparedStatement pstmtVille = null;
        PreparedStatement pstmtCP = null;
        PreparedStatement pstmtInsertVille = null;
        PreparedStatement pstmtInsertCP = null;
        PreparedStatement pstmtInsertAdresse = null;
        PreparedStatement pstmtAdresseExiste = null;

        Connection connection = DaoConnection.getInstance();

        String queryVille = "SELECT ID_VILLE FROM ville WHERE NOM_VILLE LIKE ?";
        String queryCP = "SELECT ID_CP FROM code_postal WHERE ID_VILLE = ? AND NUM_CP = ?";
        String queryInsertVille = "INSERT INTO ville (NOM_VILLE) VALUES (?)";
        String queryInsertCP = "INSERT INTO code_postal (ID_VILLE, NUM_CP) VALUES (?, ?)";
        String queryInsertAdresse = "INSERT INTO adresse (ID_CP, NUM_ADRESSE, RUE_ADRESSE) VALUES (?, ?, ?)";
        String queryAdresseExiste = "SELECT ID_ADRESSE FROM adresse WHERE ID_CP = ? AND NUM_ADRESSE = ? AND RUE_ADRESSE = ?";

        try {
            pstmtVille = connection.prepareStatement(queryVille);
            pstmtCP = connection.prepareStatement(queryCP);
            pstmtInsertVille = connection.prepareStatement(queryInsertVille, Statement.RETURN_GENERATED_KEYS);
            pstmtInsertCP = connection.prepareStatement(queryInsertCP, Statement.RETURN_GENERATED_KEYS);
            pstmtInsertAdresse = connection.prepareStatement(queryInsertAdresse, Statement.RETURN_GENERATED_KEYS);
            pstmtAdresseExiste = connection.prepareStatement(queryAdresseExiste);
            // Vérification ville existante ou création de la ville et obtention de l'id_ville
            pstmtVille.setString(1, adresse.getVille());
            ResultSet rsVille = pstmtVille.executeQuery();
            if (!rsVille.next()) {
                pstmtInsertVille.setString(1, adresse.getVille());
                pstmtInsertVille.executeUpdate();
                ResultSet generatedKeys = pstmtInsertVille.getGeneratedKeys();
                if (generatedKeys.next()) {
                    // récupération de l'ID_ville généré
                    idVille = generatedKeys.getInt(1);
                }
            } else {
                idVille = rsVille.getInt("ID_VILLE");
            }

            // Vérification du couple code Postal/Ville si existant, prise de l'ID_CP
            pstmtCP.setInt(1, idVille);
            pstmtCP.setString(2, adresse.getCodePostal());
            ResultSet rsCP = pstmtCP.executeQuery();
            if (rsCP.next()) {
                idCP = rsCP.getInt(1);
            } else {
                pstmtInsertCP.setInt(1, idVille);
                pstmtInsertCP.setString(2, adresse.getCodePostal());
                pstmtInsertCP.executeUpdate();
                ResultSet generatedKeys = pstmtInsertCP.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idCP = generatedKeys.getInt(1);
                }
            }

            // Insertion nouvelle adresse si inexistante
            pstmtAdresseExiste.setInt(1, idCP);
            pstmtAdresseExiste.setString(2, adresse.getNumero());
            pstmtAdresseExiste.setString(3, adresse.getNomRue());
            ResultSet rsAdresse = pstmtAdresseExiste.executeQuery();
            if (rsAdresse.next()) {
                idAdresse = rsAdresse.getInt(1);
            } else {
                pstmtInsertAdresse.setInt(1, idCP);
                pstmtInsertAdresse.setString(2, adresse.getNumero());
                pstmtInsertAdresse.setString(3, adresse.getNomRue());
                pstmtInsertAdresse.executeUpdate();
                ResultSet generatedKeys = pstmtInsertAdresse.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idAdresse = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            // Gestion des exceptions
            StringBuilder messageLog = new StringBuilder("Problème insertion adresse : ");
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Problème communication avec la base de données, le logiciel va fermer");
        } finally {
            if (pstmtAdresseExiste != null){
                pstmtAdresseExiste.close();
            }
            if (pstmtCP != null){
                pstmtCP.close();
            }
            if (pstmtInsertAdresse != null){
                pstmtInsertAdresse.close();
            }
            if (pstmtVille != null){
                pstmtVille.close();
            }
            if (pstmtInsertCP != null){
                pstmtInsertCP.close();
            }
            if(pstmtInsertVille != null){
                pstmtInsertVille.close();
            }
        }
        return idAdresse;
    }
}
