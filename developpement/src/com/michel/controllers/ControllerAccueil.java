package com.michel.controllers;

import com.michel.dao.DaoSociete;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.utilitaires.LoggerReverso;
import com.michel.vues.VueAccueil;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Controlleur de la vue Accueil
 */
public class ControllerAccueil {
    /**
     * lancer vue Accueil
     */
    public static void initAcceuil() {
        VueAccueil vueAccueil = new VueAccueil();
    }

    /**
     * retourne liste raison sociale
     * @param choix String client ou prospect
     * @return String[] raison sociale
     * @throws DaoException propagation à la vue
     */
    public static String[] listeSociete(String choix) throws DaoException {
        ArrayList listeSociete = DaoSociete.raisonSociales(choix);
        return (String[]) listeSociete.toArray(new String[listeSociete.size()]);
    }

    /**
     * valider
     * @param option String creer modifier supprimer
     * @param choix String client ou prospect
     * @param raisonSociale String
     * @throws ControllerException si choix incorrect
     * @throws MetierException propagation à la vue
     * @throws DaoException propagation à la vue
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
     * @param choix String client ou prospect
     * @throws MetierException propagation à la vue
     * @throws DaoException propagation à la vue
     * @throws ControllerException propagation à la vue
     */
    public static void creer(String choix)
            throws MetierException, DaoException, ControllerException {
        ControllerFormulaire.formulaireInit(choix, "creer", "");
    }

    /**
     * modifier
     * @param choix String client ou prospect
     * @param raisonSociale String
     * @throws MetierException propagation à la vue
     * @throws DaoException propagation à la vue
     * @throws ControllerException propagation à la vue
     */
    public static void modifier(String choix, String raisonSociale)
            throws MetierException, DaoException, ControllerException {
        ControllerFormulaire.formulaireInit(choix, "modifier", raisonSociale);
    }

    /**
     * supprimer
     * @param choix String client ou prospect
     * @param raisonSociale String
     * @throws MetierException propagation à la vue
     * @throws DaoException propagation à la vue
     * @throws ControllerException propagation à la vue
     */
    public static void supprimer(String choix, String raisonSociale)
            throws MetierException, DaoException, ControllerException {
        ControllerFormulaire.formulaireInit(choix, "supprimer", raisonSociale);
    }

    /**
     * afficher
     * @param choix client ou prospect
     * @throws MetierException propagation à la vue
     * @throws DaoException propagation à la vue
     * @throws ControllerException propagation à la vue
     */
    public static void afficher(String choix)
            throws MetierException, DaoException, ControllerException {
        ControllerAffichage.affichageInit(choix);
    }
}
