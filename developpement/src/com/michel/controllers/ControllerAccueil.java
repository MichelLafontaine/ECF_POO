package com.michel.controllers;

import com.michel.dao.DaoSociete;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.utilitaires.LoggerReverso;
import com.michel.vues.VueAccueil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class ControllerAccueil {
    /**
     * initAccueil
     */
    public static void initAcceuil() {
        VueAccueil vueAccueil = new VueAccueil();
    }

    /**
     * listeSociete
     * @param choix
     * @return
     * @throws DaoException
     */
    public static String[] listeSociete(String choix) throws DaoException {
        ArrayList listeSociete = DaoSociete.raisonSociales(choix);
        return (String[]) listeSociete.toArray(new String[listeSociete.size()]);
    }

    /**
     * valider
     * @param option
     * @param choix
     * @param raisonSociale
     * @throws ControllerException
     * @throws SQLException
     * @throws MetierException
     * @throws DaoException
     */
    public static void valider(String option, String choix, String raisonSociale)
            throws ControllerException,  MetierException, DaoException {
        switch (option) {
            case "creer" :
                creer(choix);
                break;
            case "modifier" :
                modifier(choix, raisonSociale);
                break;
            case "supprimer" :
                supprimer(choix, raisonSociale);
                break;
            case "afficher" :
                afficher(choix);
                break;
            default:
                LoggerReverso.LOGGER.log(Level.SEVERE, "erreur option");
                throw new ControllerException("Erreur Accueil");
        }
    }

    /**
     * creer
     * @param choix
     * @throws MetierException
     * @throws DaoException
     * @throws ControllerException
     */
    public static void creer(String choix)
            throws MetierException, DaoException, ControllerException {
        ControllerFormulaire.formulaireInit(choix, "creer", "");
    }

    /**
     * modifier
     * @param choix
     * @param raisonSociale
     * @throws MetierException
     * @throws DaoException
     * @throws ControllerException
     */
    public static void modifier(String choix, String raisonSociale)
            throws MetierException, DaoException, ControllerException {
        ControllerFormulaire.formulaireInit(choix, "modifier", raisonSociale);
    }

    /**
     * supprimer
     * @param choix
     * @param raisonSociale
     * @throws MetierException
     * @throws DaoException
     * @throws ControllerException
     */
    public static void supprimer(String choix, String raisonSociale)
            throws MetierException, DaoException, ControllerException {
        ControllerFormulaire.formulaireInit(choix, "supprimer", raisonSociale);
    }

    /**
     * afficher
     * @param choix
     * @throws SQLException
     * @throws MetierException
     * @throws DaoException
     * @throws ControllerException
     */
    public static void afficher(String choix)
            throws MetierException, DaoException, ControllerException {
        ControllerAffichage.affichageInit(choix);
    }
}
