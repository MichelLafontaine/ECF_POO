package com.michel.dao;

import com.michel.exceptions.DaoException;
import com.michel.utilitaires.LoggerReverso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * recherche lié à la table societe de la BDD
 */
public class DaoSociete {

    /**
     * retourne une liste de NOM_SOCIETE de la table societe present dans la table client ou prospect
     * @param choixClientProspect String client ou prospect
     * @return ArrayList de raison Sociale
     * @throws DaoException si pb avec la BDD
     */

    //liste des raisons sociales
    public static ArrayList raisonSociales (String choixClientProspect) throws DaoException {

        ArrayList<String> societes = new ArrayList<>();
        societes.add(" ");
        String querySociete = "SELECT NOM_SOCIETE FROM societe " +
                "INNER JOIN " + choixClientProspect + " cp ON cp.ID_SOCIETE = societe.ID_SOCIETE;";
        try(Statement stmt = DaoConnection.getInstance().createStatement()){
            ResultSet rsSociete = stmt.executeQuery(querySociete);
            while (rsSociete.next()){
                societes.add(rsSociete.getString(1));
            }
        } catch (SQLException sqlException){
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture BDD" +
                    sqlException.getMessage() + " " + sqlException);
            throw new DaoException(2, "problème connection base de donnée, le logiciel va fermer");
        }
        return societes;
    }
}
