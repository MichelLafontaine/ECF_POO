package com.michel.dao;

import com.michel.utilitaires.LoggerReverso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

public class DaoSociete {

    public static ArrayList raisonSociales (String choixClientProspect) throws SQLException {

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
        }
        return societes;
    }
}
