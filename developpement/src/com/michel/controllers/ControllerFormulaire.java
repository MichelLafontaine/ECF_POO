package com.michel.controllers;

import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Client;
import com.michel.metiers.Prospect;
import com.michel.utilitaires.ChoixClientProspect;
import com.michel.utilitaires.EnumOption;
import com.michel.utilitaires.LoggerReverso;
import com.michel.vues.VueFormulaire;

import java.util.logging.Level;

/**
 * Controlleur de la vue modification/suppression/création
 */
public class ControllerFormulaire {
    private int identifiant;
    private ChoixClientProspect choix;
    private EnumOption option;
    private String raisonSociale;
    private static final String MESSAGE_ERREUR = "Erreur, le logiciel va fermer";
    private static final String ERREUR_CHOIX ="problème choix client/prospect";


    /**
     * constructeur Controller formulaire sans attribut
     */
    public ControllerFormulaire() {
    }

    /**
     * Constructeur Controller formulaire choix et option
     * @param choix ChoixClientProspect
     * @param option EnumOption
     */
    public ControllerFormulaire(ChoixClientProspect choix, EnumOption option) {
        this.choix = choix;
        this.option = option;
    }

    /**
     * Contructeur Controller formulaire sans identifiant
     * @param choix String client ou prospect
     * @param option String creer modifier supprimer
     * @param raisonSociale String
     */
    public ControllerFormulaire(ChoixClientProspect choix, EnumOption option, String raisonSociale) {
        this.choix = choix;
        this.option = option;
        this.raisonSociale = raisonSociale;
    }

    public ControllerFormulaire(ChoixClientProspect choix, EnumOption option, int identifiant) {
        this.choix = choix;
        this.option = option;
        this.identifiant = identifiant;
    }

    /**
     * formualireInit
     * @throws MetierException renvoi à la vue
     * @throws DaoException renvoi à la vue
     * @throws ControllerException  renvoi à la vue
     */
    public void formulaireInit()
            throws Exception {
        if (option.equals(EnumOption.CREER)){
            new VueFormulaire(choix, option);
        } else if (option.equals(EnumOption.MODIFIER) || option.equals(EnumOption.SUPPRIMER)){
            if (choix.equals(ChoixClientProspect.CLIENT)){
                Client client = DaoClient.findByName(raisonSociale);
                new VueFormulaire(choix, option, client);
            } else if (choix.equals(ChoixClientProspect.PROSPECT)){
                Prospect prospect = DaoProspect.findByName(raisonSociale);
                new VueFormulaire(choix, option, prospect);

            } else {
                LoggerReverso.LOGGER.log(Level.SEVERE, ERREUR_CHOIX);
                throw new ControllerException(MESSAGE_ERREUR);
            }
        }
    }

    /**
     * creer
     * @param entite Client ou Prospect
     * @throws Exception
     */
    public void creer(Object entite)
            throws Exception{
        if (choix.equals(ChoixClientProspect.CLIENT)){
            DaoClient.create((Client) entite);
        } else if (choix.equals(ChoixClientProspect.PROSPECT)){
            DaoProspect.create((Prospect) entite);
        } else {
            LoggerReverso.LOGGER.log(Level.SEVERE, ERREUR_CHOIX);
            throw new ControllerException(MESSAGE_ERREUR);
        }

    }

    /**
     * modifier
     * @param entite Client ou Prospect
     * @throws Exception
     */
    public void modifier(Object entite)
            throws Exception {
        if (choix.equals(ChoixClientProspect.CLIENT)){
            DaoClient.update((Client) entite);
        }
        if (choix.equals(ChoixClientProspect.PROSPECT)){
            DaoProspect.update((Prospect) entite);
        }

    }

    /**
     * supprimer
     * @throws DaoException propagation à la vue
     * @throws ControllerException si choix pas bon
     */
    public void supprimer() throws Exception {
        if (this.choix.equals(ChoixClientProspect.CLIENT)){
            DaoClient.deleteClient(identifiant);
        } else if (this.choix.equals(ChoixClientProspect.PROSPECT)) {
            DaoProspect.delete(identifiant);
        } else {
            LoggerReverso.LOGGER.log(Level.SEVERE, ERREUR_CHOIX);
            throw new ControllerException(MESSAGE_ERREUR);
        }
    }

    /**
     *  valider
     * @param entite Client ou Prospect
     * @throws Exception
     */
    public void valider (Object entite)
            throws Exception {
        switch (option){
            case EnumOption.CREER:
                creer(entite);
                break;
            case EnumOption.MODIFIER:
                modifier(entite);
                break;
            case EnumOption.SUPPRIMER:
                supprimer();
                break;
            default:
                LoggerReverso.LOGGER.log(Level.SEVERE, "problème option");
                throw new ControllerException(MESSAGE_ERREUR);
        }
    }

    /**
     * reour accueil
     */
    public static void accueil() {
        ControllerAccueil.initAcceuil();
    }
}
