package com.michel.dao;


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

    public static void createClient (Client client) throws SQLException, MetierException {

        // Vérifier si la raison sociale n'est pas existante
        // en cas d'existante DAO EXCEPTION
        // si n'existe pas création dans client/societe et lancer creer Adresse si pas doublon
    }
}