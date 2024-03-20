package com.michel.controllers;

import com.michel.dao.DaoSociete;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.utilitaires.ChoixClientProspect;
import com.michel.utilitaires.EnumOption;
import com.michel.utilitaires.LoggerReverso;
import com.michel.vues.VueAccueil;

import java.util.List;
import java.util.logging.Level;

/**
 * Controlleur de la vue Accueil
 */
public class ControllerAccueil {
    private String raisonSociale;
    private EnumOption option;
    private ChoixClientProspect choix;
    /**
     * lancer vue Accueil
     */
    public static void initAcceuil() {
        new VueAccueil();
    }


    /**
     * constructeur sans attribut
     */
    public ControllerAccueil() {
    }

    /**
     * Constructeur
     * @param choix String Classe ENUM ChoixClientProspect
     */
    public ControllerAccueil(ChoixClientProspect choix, EnumOption option, String raisonSociale) {
        this.choix = choix;
        this.option = option;
        this.raisonSociale = raisonSociale;
    }

    /**
     * retourne liste raison sociale
     * @return String[] raison sociale
     * @throws DaoException propagation à la vue
     */
    public static String[] listeSociete(ChoixClientProspect choix) throws Exception {
        List<String> listeSociete = DaoSociete.raisonSociales(choix);
        return listeSociete.toArray(new String[0]);
    }

    /**
     * valider
     * @throws ControllerException si choix incorrect
     * @throws MetierException propagation à la vue
     * @throws DaoException propagation à la vue
     */
    public void validerChoix()
            throws Exception {
        
        switch (option) {
            case EnumOption.CREER, EnumOption.MODIFIER, EnumOption.SUPPRIMER:
                ControllerFormulaire controllerFormulaire = new ControllerFormulaire(choix, option, raisonSociale);
                controllerFormulaire.formulaireInit();
                break;
            case EnumOption.AFFICHER:
                ControllerAffichage controllerAffichage = new ControllerAffichage(choix);
                controllerAffichage.affichageInit();
                break;
            default:
                LoggerReverso.LOGGER.log(Level.SEVERE, "erreur option");
                throw new ControllerException("Erreur Accueil");
        }
    }
}
