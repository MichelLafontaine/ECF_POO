package com.michel.dao;

import com.michel.exceptions.DaoException;
import com.michel.utilitaires.LoggerReverso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

public class DaoConnection {

    private static Connection connection = null;
    final Properties dataProperties = new Properties();
    public DaoConnection() {

        try {
            File fichier = new File("database.properties");
            FileInputStream input = new FileInputStream(fichier);
            dataProperties.load(input);
            connection = DriverManager.getConnection(
                    dataProperties.getProperty("url"),
                    dataProperties.getProperty("login"),
                    dataProperties.getProperty("password")
            );
            LoggerReverso.LOGGER.log(Level.INFO, "ouverture BDD");
            System.out.println("connecter à la BDD");
        } catch (SQLException e) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème ouverture BDD SQLException"+
                    e.getMessage() + " " + e);
            System.out.println("erreur connection");
        } catch (FileNotFoundException fe) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème ouverture BDD FileNotFoundException"+
                    fe.getMessage() + " " + fe);
            System.out.println("problème fichier");
        } catch (IOException ie) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème ouverture BDD IOException"+
                    ie.getMessage() + " " + ie);
            System.out.println("problème ioexception");
        } catch (Exception exception) {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème Ouverture BDD"+
                    exception.getMessage() + " " + exception);
            System.out.println("autre exception");
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (connection != null) {
                try {
                    LoggerReverso.LOGGER.log(Level.INFO, "fermeture BDD");
                    connection.close();
                    System.out.println("Fermeture de la BDD");
                } catch (SQLException ex) {
                    LoggerReverso.LOGGER.log(Level.SEVERE, "problème fermeture BDD"+ ex.getMessage() + " " + ex);
                }
            }
        }));
    }

    public static Connection getInstance() throws DaoException {
        if (connection == null) {
            try{
                new DaoConnection();
            }catch (Exception e){
                LoggerReverso.LOGGER.log(Level.SEVERE, "problème ouverture BDD SQLException"+
                        e.getMessage() + " " + e);
                throw new DaoException(2, "problème de connection avec la base de données, le logiciel va fermer");
            }
        }
        return connection;
    }
}
