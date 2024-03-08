package com.michel.controllers;

import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Client;
import com.michel.metiers.Prospect;
import com.michel.utilitaires.ChoixClientProspect;
import com.michel.utilitaires.EnumOption;
import com.michel.utilitaires.LoggerReverso;
import com.michel.vues.VueFormulaire;

import java.time.LocalDate;
import java.util.logging.Level;

/**
 * Controlleur de la vue modification/suppression/création
 */
public class ControllerFormulaire {// Noncompliant

    private int identifiant;
    private int interetInt;
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

    /**
     * Contructeur Controller formulaire
     * @param choix String client ou prospect
     * @param option String creer modifier supprimer
     * @param raisonSociale String
     * @param identifiant int
     */
    public ControllerFormulaire(ChoixClientProspect choix, EnumOption option, String raisonSociale, int identifiant) {
        this.choix = choix;
        this.option = option;
        this.raisonSociale = raisonSociale;
        this.identifiant = identifiant;
    }

    /**
     * formualireInit
     * @throws MetierException renvoi à la vue
     * @throws DaoException renvoi à la vue
     * @throws ControllerException  renvoi à la vue
     */
    public void formulaireInit()
            throws MetierException, DaoException, ControllerException {
        VueFormulaire vueFormulaire = new VueFormulaire(choix, option);
        vueFormulaire.setDate(LocalDate.of(1900,1,1));
        vueFormulaire.setRaisonSociale(raisonSociale);
        // Initilisation de la vue Formulaire en cas de modification ou suppression
        if (option.equals(EnumOption.MODIFIER) || option.equals(EnumOption.SUPPRIMER)){
            if (choix.equals(ChoixClientProspect.CLIENT)){
                Client client = DaoClient.findByName(raisonSociale);
                vueFormulaire.setIdentifiant(client.getIdentifiant());
                vueFormulaire.setNumero(client.getAdresse().getNumero());
                vueFormulaire.setNomRue(client.getAdresse().getNomRue());
                vueFormulaire.setCodePostal(client.getAdresse().getCodePostal());
                vueFormulaire.setVille(client.getAdresse().getVille());
                vueFormulaire.setEmail(client.getEmail());
                vueFormulaire.setTelephone(client.getTelephone());
                vueFormulaire.setCA(client.getChiffreAffaire());
                vueFormulaire.settNbreEmploye(String.valueOf(client.getNbreEmploye()));
                vueFormulaire.setCommentaire(client.getCommentaire());
            } else if (choix.equals(ChoixClientProspect.PROSPECT)){
                Prospect prospect = DaoProspect.findByName(raisonSociale);
                vueFormulaire.setIdentifiant(prospect.getIdentifiant());
                vueFormulaire.setNumero(prospect.getAdresse().getNumero());
                vueFormulaire.setNomRue(prospect.getAdresse().getNomRue());
                vueFormulaire.setCodePostal(prospect.getAdresse().getCodePostal());
                vueFormulaire.setVille(prospect.getAdresse().getVille());
                vueFormulaire.setEmail(prospect.getEmail());
                vueFormulaire.setTelephone(prospect.getTelephone());
                vueFormulaire.setCommentaire(prospect.getCommentaire());
                // Transformation 1 ou 0 en oui ou non
                if (prospect.getInteretProspect() == 1){
                    vueFormulaire.setInteret("oui");
                } else if(prospect.getInteretProspect() == 0){
                    vueFormulaire.setInteret("non");
                } else {
                    LoggerReverso.LOGGER.log(Level.SEVERE, "problème choix oui/non");
                    throw new ControllerException(MESSAGE_ERREUR);
                }
                vueFormulaire.setDate(prospect.getDateProspect());
                vueFormulaire.setjComboBoxJours(prospect.getDateProspect().getDayOfMonth());
                vueFormulaire.setjComboBoxAnnee(String.valueOf(prospect.getDateProspect().getYear()));
                vueFormulaire.setjComboBoxMois(prospect.getDateProspect().getMonthValue());

            } else {
                LoggerReverso.LOGGER.log(Level.SEVERE, ERREUR_CHOIX);
                throw new ControllerException(MESSAGE_ERREUR);
            }
        }
    }

    /**
     * creer
     * @param numero String ne doit pas etre null
     * @param nomRue String ne doit pas etre null
     * @param codePostal String ne doit pas etre null 5 chiffres
     * @param ville String ne doit pas etre null
     * @param telephone String ne doit pas etre null
     * @param email String ne doit pas etre null
     * @param commentaire String
     * @param ca Double ne doit pas être null
     * @param nbreEmploye int ne doit pas etre null
     * @param dateProspect LocalDate ne doit pas etre null
     * @param interet String oui ou non
     * @throws MetierException envoi vue formulaire mauvaise rentrer
     * @throws DaoException envoi formulaire
     * @throws ControllerException si erreur choix ou interet
     */
    public void creer(String numero, String nomRue, String codePostal, String ville,
                             String telephone, String email, String commentaire, double ca,
                             int nbreEmploye, LocalDate dateProspect, String interet)
            throws MetierException, DaoException, ControllerException {
        Adresse adresse = new Adresse(numero, nomRue, ville, codePostal);

        if (choix.equals(ChoixClientProspect.CLIENT)){
            Client client = new Client(raisonSociale, email, telephone, commentaire, adresse, ca, nbreEmploye);
            DaoClient.create(client);
        } else if (choix.equals(ChoixClientProspect.PROSPECT)){
            interetInt = -1;
            if (interet.equals("oui")){
                interetInt = 1;
            } else if (interet.equals("non")) {
                interetInt = 0;
            } else {
                LoggerReverso.LOGGER.log(Level.SEVERE, "problème choix oui/non");
                throw new ControllerException(MESSAGE_ERREUR);
            }
            Prospect prospect = new Prospect(raisonSociale, email, telephone, commentaire,
                    adresse, dateProspect, interetInt);
            DaoProspect.create(prospect);
        } else {
            LoggerReverso.LOGGER.log(Level.SEVERE, ERREUR_CHOIX);
            throw new ControllerException(MESSAGE_ERREUR);
        }

    }

    /**
     * modifier
     * @param numero String ne doit pas etre null
     * @param nomRue String ne doit pas etre null
     * @param codePostal String ne doit pas etre null
     * @param ville String ne doit pas etre null
     * @param telephone String ne doit pas etre null
     * @param email String ne doit pas etre null
     * @param commentaire String ne doit pas etre null
     * @param ca Double ne doit pas etre null
     * @param nbreEmploye int ne doit pas etre null
     * @param dateProspect LocalDate ne doit pas etre null
     * @param interet String oui ou non
     * @throws MetierException envoi au formulaire
     * @throws DaoException envoi au formulaire
     * @throws ControllerException si interet ou choix non correct
     */
    public void modifier(String numero, String nomRue, String codePostal, String ville,
                                String telephone, String email, String commentaire, double ca,
                                int nbreEmploye, LocalDate dateProspect, String interet)
            throws MetierException, DaoException, ControllerException {
        Adresse adresse = new Adresse(numero, nomRue, ville, codePostal);
        interetInt = -1;

        if (choix.equals(ChoixClientProspect.CLIENT)){
            Client client = new Client(identifiant, raisonSociale, email, telephone, commentaire, adresse, ca, nbreEmploye);
            DaoClient.update(client, identifiant);
        }
        if (choix.equals(ChoixClientProspect.PROSPECT)){
            if (interet.equals("oui")){
                interetInt = 1;
            } else if (interet.equals("non")) {
                interetInt = 0;
            } else {
                LoggerReverso.LOGGER.log(Level.SEVERE, ERREUR_CHOIX);
                throw new ControllerException(MESSAGE_ERREUR);
            }
            Prospect prospect = new Prospect(identifiant, raisonSociale, email, telephone, commentaire,
                    adresse, dateProspect, interetInt);
            DaoProspect.update(prospect, identifiant);
        }

    }

    /**
     * supprimer
     * @throws DaoException propagation à la vue
     * @throws ControllerException si choix pas bon
     */
    public void supprimer() throws DaoException, ControllerException {
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
     * valider
     * @param numero String ne doit pas etre null
     * @param nomRue String ne doit pas etre null
     * @param codePostal String ne doit pas etre null
     * @param ville String ne doit pas etre null
     * @param email String ne doit pas etre null
     * @param telephone String ne doit pas etre null
     * @param ca Double ne doit pas etre null
     * @param nbreEmploye int ne doit pas etre null
     * @param date LocalDate ne doit pas etre null
     * @param interet oui ou non
     * @param commentaire String
     * @throws ControllerException si option incorrect
     * @throws MetierException propagation à la vue
     * @throws DaoException propagation à la vue
     */
    public void valider (String numero, String nomRue, String codePostal, String ville,
                                String email, String telephone, double ca, int nbreEmploye, LocalDate date,
                                String interet, String commentaire)
            throws ControllerException, MetierException, DaoException {
        switch (option){
            case EnumOption.CREER:
                creer(numero, nomRue, codePostal, ville, telephone, email, commentaire,
                        ca, nbreEmploye, date, interet);
                break;
            case EnumOption.MODIFIER:
                modifier(numero, nomRue, codePostal, ville, telephone, email, commentaire,
                        ca, nbreEmploye, date, interet);
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
