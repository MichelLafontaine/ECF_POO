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

/**
 * Classe de connection et fermeture avec la BDD
 */
public class DaoConnection {
    private static Connection connection = null;
    final Properties dataProperties = new Properties();
//(InputStream input = Files.newInputStream(path))
    /**
     * Connection BDD
     */
    private DaoConnection() throws DaoException {
        File fichier = new File("database.properties");
        try (FileInputStream input = new FileInputStream(fichier);){
            dataProperties.load(input);
            connection = DriverManager.getConnection(
                    dataProperties.getProperty("url"),
                    dataProperties.getProperty("login"),
                    dataProperties.getProperty("password")
            );
        } catch (SQLException e) {
            StringBuilder messageLog = new StringBuilder("problème ouverture BDD SQLException, ");
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Problème survenue dans la Base de Données");
        } catch (FileNotFoundException e) {
            StringBuilder messageLog = new StringBuilder("problème ouverture BDD FileNotFoundException, ");
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Problème survenue dans la Base de Données");
        } catch (IOException e) {
            StringBuilder messageLog = new StringBuilder("problème ouverture BDD IOException, ");
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Problème survenue dans la Base de Données");
        } catch (Exception e) {
            StringBuilder messageLog = new StringBuilder("problème Ouverture BDD, ");
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "Problème survenue dans la Base de Données");
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (connection != null) {
                try {
                    LoggerReverso.LOGGER.log(Level.INFO, "fermeture BDD");
                    connection.close();
                } catch (SQLException e) {
                    StringBuilder messageLog = new StringBuilder("problème fermeture BDD, ");
                    messageLog.append(e.getMessage()).append(" ").append(e);
                    LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
                }
            }
        }));
    }

    /**
     * création d'une seule et unique connection avec BDD
     * Singleton Connection
     * @return Connection
     * @throws DaoException si pb connection BDD
     */
    public static Connection getInstance() throws DaoException {
        if (connection == null) {
            new DaoConnection();
            LoggerReverso.LOGGER.log(Level.INFO, "connection BDD réussite");
        }
        return connection;
    }
}
