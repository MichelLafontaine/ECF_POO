package com.michel.dao;

import com.michel.exceptions.DaoException;
import com.michel.utilitaires.ChoixClientProspect;
import com.michel.utilitaires.LoggerReverso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.michel.dao.DaoConnection.getInstance;

/**
 * recherche lié à la table societe de la BDD
 */
public class DaoSociete {

    private DaoSociete() {
    }

    /**
     * retourne une liste de NOM_SOCIETE de la table societe present dans la table client ou prospect
     *
     * @param choix Classe ENUM ChoixClientProspect client ou prospect
     * @return ArrayList de raison Sociale
     * @throws DaoException si pb avec la BDD
     */

    //liste des raisons sociales
    public static List<String> raisonSociales(ChoixClientProspect choix) throws DaoException, SQLException {
        PreparedStatement pstmt = null;

        Connection connection = getInstance();

        List<String> societes = new ArrayList<>();
        societes.add(" ");
        String querySociete = null;

        if (ChoixClientProspect.CLIENT.equals(choix)) {
            querySociete = "SELECT NOM_SOCIETE FROM societe INNER JOIN client ON client.ID_SOCIETE = societe.ID_SOCIETE;";
        } else if (ChoixClientProspect.PROSPECT.equals(choix)) {
            querySociete = "SELECT NOM_SOCIETE FROM societe " +
                    "INNER JOIN prospect ON prospect.ID_SOCIETE = societe.ID_SOCIETE;";
        } else {
            throw new DaoException(2, "problème configuration choix client ou Prospect ");
        }

        try {
            pstmt = connection.prepareStatement(querySociete);
            ResultSet rsSociete = pstmt.executeQuery();
            while (rsSociete.next()) {
                societes.add(rsSociete.getString(1));
            }
        } catch (SQLException sqlException) {
            StringBuilder messageLog = new StringBuilder("problème lecture BDD, ");
            messageLog.append(sqlException.getMessage()).append(" ").append(sqlException);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            throw new DaoException(2, "problème connection base de donnée, le logiciel va fermer");
        } finally {
            if (pstmt != null){
                pstmt.close();
            }

        }
        return societes;
    }
}
