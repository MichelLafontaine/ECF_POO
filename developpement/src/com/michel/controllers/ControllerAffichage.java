package com.michel.controllers;

import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.utilitaires.ChoixClientProspect;
import com.michel.utilitaires.LoggerReverso;
import com.michel.vues.VueAfficher;

import java.util.List;
import java.util.logging.Level;

/**
 * Controlleur de la vue Affichage
 */
public class ControllerAffichage {

    private ChoixClientProspect choix;

    public ControllerAffichage() {
    }

    public ControllerAffichage(ChoixClientProspect choix) {
        this.choix = choix;
    }

    /**
     * lancer la vue de l'affichage
     */
    public void affichageInit() {
        new VueAfficher(choix);
    }

    /**
     * reour accueil
     */
    public static void accueil() {
        ControllerAccueil.initAcceuil();
    }

    /**
     * finAll
     * @return String[][] de toutes les données Objects Client ou Prospect
     * @throws MetierException propagation à la vue
     * @throws DaoException propagation à la vue
     * @throws ControllerException choix incorrect
     */
    public List findAll() throws Exception {
        if (choix.equals(ChoixClientProspect.CLIENT)){
            return DaoClient.findAll();
        } else if (choix.equals(ChoixClientProspect.PROSPECT)){
            return DaoProspect.findAll();
        } else {
            LoggerReverso.LOGGER.log(Level.SEVERE, "erreur option");
            throw new ControllerException("Erreur logiciel");
        }
    }


}
