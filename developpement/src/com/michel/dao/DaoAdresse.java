package com.michel.dao;

import com.michel.exceptions.DaoException;
import com.michel.metiers.Adresse;
import com.michel.utilitaires.LoggerReverso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class DaoAdresse {
    public static int creerAdresse (Adresse adresse) throws DaoException {

        int idAdresse = 0;
        int idVille = 0;
        String queryVille = "SELECT NOM_VILLE FROM ville WHERE NOM_VILLE LIKE '" + adresse.getVille() + "';";
        String queryIdVIlle = "SELECT ID_VILLE FROM ville WHERE NOM_VILLE LIKE '" + adresse.getVille() + "';";
        int idCP = 0;
        String queryCP = "SELECT ID_CP, ID_VILLE, NUM_CP FROM code_postal";

        try (Statement stmt = DaoConnection.getInstance().createStatement()) {
            // Vérification ville existante ou création de la ville et obtention de l'id_ville
            ResultSet rsNom = stmt.executeQuery(queryVille);
            if (!rsNom.next()) {
                stmt.execute("INSERT INTO `ville` (`ID_VILLE`, `NOM_VILLE`) " +
                        "VALUES (NULL, '" + adresse.getVille() + "');");
            }
            //recherche de l'ID_ville dans la table ville
            ResultSet rsId = stmt.executeQuery(queryIdVIlle);
            while (rsId.next()) {
                idVille = rsId.getInt("ID_VILLE");
            }

            //verification du couple code Postal/Ville si existant prise de l'ID_CP
            ResultSet rsCP = stmt.executeQuery(queryCP);
            while (rsCP.next()) {
                if (rsCP.getInt("NUM_CP") != 0 && rsCP.getInt("ID_VILLE") == idVille) {
                    idCP = rsCP.getInt("ID_CP");
                }
            }
            //Si coupe CP/Ville inexistant création
            if (idCP == 0) {
                stmt.execute("INSERT INTO `code_postal` (`ID_CP`, `ID_VILLE`, `NUM_CP`) " +
                        "VALUES (NULL, '" + idVille + "', '" + adresse.getCodePostal() + "');", Statement.RETURN_GENERATED_KEYS);
                ResultSet rsIdCP = stmt.getGeneratedKeys();
                while (rsIdCP.next()){
                    idCP = rsIdCP.getInt(1);
                }
            }

            // Insetion nouvelle adresse si inexistante
            String queryAdresseExiste = "SELECT * FROM adresse WHERE ID_CP = " + idCP +
                    " AND NUM_ADRESSE = '" + adresse.getNumero() + "' AND RUE_ADRESSE = '" + adresse.getNomRue() + "';";
            ResultSet rsAdresse = stmt.executeQuery(queryAdresseExiste);
            //si vide inserer nouvelle adresse + retour clé primaire créer
            while (rsAdresse.next()){
                idAdresse = rsAdresse.getInt(1);
            }
            if (idAdresse == 0) {
                stmt.execute("INSERT INTO adresse (ID_ADRESSE, ID_CP, NUM_ADRESSE, RUE_ADRESSE) " +
                        "VALUES (NULL, " + idCP + ", '" + adresse.getNumero() + "', '" + adresse.getNomRue() + "');",
                        Statement.RETURN_GENERATED_KEYS);
                ResultSet rsIdAdresse = stmt.getGeneratedKeys();
                while (rsIdAdresse.next()) {
                    idAdresse = rsIdAdresse.getInt(1);
                }
            // si existante retour de la clé primaire existante
            }
        } catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème insertion adresse" +
                e.getMessage() + " " + e);
            throw new DaoException(2, "Problème communication avec la base de données, le logiciel va fermer");

        }
        return idAdresse;
    }
}
