package com.michel.dao;

import com.michel.exceptions.DaoException;
import com.michel.utilitaires.LoggerReverso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

public class DaoSociete {

    /**
     * raisonSociale
     * @param choixClientProspect
     * @return
     * @throws DaoException
     */

    //liste des raisons sociales
    public static ArrayList raisonSociales (String choixClientProspect) throws DaoException {

        ArrayList<String> societes = new ArrayList<>();
        societes.add("");
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
