package com.michel.controllers;

import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Client;
import com.michel.metiers.Prospect;
import com.michel.utilitaires.LoggerReverso;
import com.michel.vues.VueFormulaire;

import java.time.LocalDate;
import java.util.logging.Level;

/**
 * Controlleur de la vue modification/suppression/création
 */
public class ControllerFormulaire {

    private static int identifiant;

    /**
     * formualireInit
     * @param choixClientProspect String client ou prospect
     * @param option String creer modifier ou supprimer
     * @param raisonSociale String raison sociale
     * @throws MetierException renvoi à la vue
     * @throws DaoException renvoi à la vue
     * @throws ControllerException  renvoi à la vue
     */
    public static void formulaireInit(String choixClientProspect, String option, String raisonSociale)
            throws MetierException, DaoException, ControllerException {
        VueFormulaire vueFormulaire = new VueFormulaire(choixClientProspect, option);
        // Initialisation date dans la vue du formulaire;
        vueFormulaire.setDate(LocalDate.of(1900,1,1));
        // Initilisation de la vue Formulaire en cas de modification ou suppression
        if (option.equals("modifier") || option.equals("supprimer")){
            if (choixClientProspect.equals("client")){
                Client client = DaoClient.findByName(raisonSociale);
                vueFormulaire.setNumero(client.getAdresse().getNumero());
                vueFormulaire.setNomRue(client.getAdresse().getNomRue());
                vueFormulaire.setCodePostal(client.getAdresse().getCodePostal());
                vueFormulaire.setVille(client.getAdresse().getVille());
                vueFormulaire.setEmail(client.getEmail());
                vueFormulaire.setTelephone(client.getTelephone());
                vueFormulaire.setCA(client.getChiffreAffaire());
                vueFormulaire.settNbreEmploye(String.valueOf(client.getNbreEmploye()));
                vueFormulaire.setCommentaire(client.getCommentaire());
                identifiant = client.getIdentifiant();
            } else if (choixClientProspect.equals("prospect")){
                Prospect prospect = DaoProspect.findByName(raisonSociale);
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
                    throw new ControllerException("Erreur, le logiciel va fermer");
                }
                vueFormulaire.setDate(prospect.getDateProspect());
                vueFormulaire.setjComboBoxJours(prospect.getDateProspect().getDayOfMonth());
                vueFormulaire.setjComboBoxAnnee(String.valueOf(prospect.getDateProspect().getYear()));
                vueFormulaire.setjComboBoxMois(prospect.getDateProspect().getMonthValue());
                identifiant = prospect.getIdentifiant();
            } else {
                LoggerReverso.LOGGER.log(Level.SEVERE, "problème choix client/prospect");
                throw new ControllerException("Erreur, le logiciel va fermer");
            }
            vueFormulaire.setRaisonSociale(raisonSociale);

        }
    }

    /**
     * creer
     * @param raisonSociale String ne doit pas etre null
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
     * @param choix String client ou prospect
     * @throws MetierException envoi vue formulaire mauvaise rentrer
     * @throws DaoException envoi formulaire
     * @throws ControllerException si erreur choix ou interet
     */
    public static void creer(String raisonSociale, String numero, String nomRue, String codePostal, String ville,
                             String telephone, String email, String commentaire, double ca,
                             int nbreEmploye, LocalDate dateProspect, String interet, String choix)
            throws MetierException, DaoException, ControllerException {
        Adresse adresse = new Adresse(numero, nomRue, ville, codePostal);
        int interetInt = -1;
        if (choix.equals("client")){
            Client client = new Client(raisonSociale, email, telephone, commentaire, adresse, ca, nbreEmploye);
            DaoClient.create(client);
        } else if (choix.equals("prospect")){
            if (interet.equals("oui")){
                interetInt = 1;
            } else if (interet.equals("non")) {
                interetInt = 0;
            } else {
                LoggerReverso.LOGGER.log(Level.SEVERE, "problème choix oui/non");
                throw new ControllerException("Erreur, le logiciel va fermer");
            }
            Prospect prospect = new Prospect(raisonSociale, email, telephone, commentaire,
                    adresse, dateProspect, interetInt);
            DaoProspect.create(prospect);
        } else {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème choix client/prospect");
            throw new ControllerException("Erreur, le logiciel va fermer");
        }

    }

    /**
     * modifier
     * @param raisonSociale String ne doit pas etre null
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
     * @param choix String client ou porspect
     * @throws MetierException envoi au formulaire
     * @throws DaoException envoi au formulaire
     * @throws ControllerException si interet ou choix non correct
     */
    public static void modifier(String raisonSociale, String numero, String nomRue, String codePostal, String ville,
                                String telephone, String email, String commentaire, double ca,
                                int nbreEmploye, LocalDate dateProspect, String interet, String choix)
            throws MetierException, DaoException, ControllerException {
        Adresse adresse = new Adresse(numero, nomRue, ville, codePostal);
        int interetInt = -1;

        if (choix.equals("client")){
            Client client = new Client(identifiant, raisonSociale, email, telephone, commentaire, adresse, ca, nbreEmploye);
            DaoClient.update(client, identifiant);
        }
        if (choix.equals("prospect")){
            if (interet.equals("oui")){
                interetInt = 1;
            } else if (interet.equals("non")) {
                interetInt = 0;
            } else {
                LoggerReverso.LOGGER.log(Level.SEVERE, "problème choix client/prospect");
                throw new ControllerException("Erreur, le logiciel va fermer");
            }
            Prospect prospect = new Prospect(identifiant, raisonSociale, email, telephone, commentaire,
                    adresse, dateProspect, interetInt);
            DaoProspect.update(prospect, identifiant);
        }

    }

    /**
     * supprimer
     * @param choix String client ou prospect
     * @throws DaoException propagation à la vue
     * @throws ControllerException si choix pas bon
     */
    public static void supprimer(String choix) throws DaoException, ControllerException {
        if (choix.equals("client")){
            DaoClient.deleteClient(identifiant);
        } else if (choix.equals("prospect")) {
            DaoProspect.delete(identifiant);
        } else {
            LoggerReverso.LOGGER.log(Level.SEVERE, "problème choix client/prospect");
            throw new ControllerException("Erreur, le logiciel va fermer");
        }
    }

    /**
     * valider
     * @param raisonSocial String ne doit pas etre null
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
     * @param choix String client ou prospect
     * @param option String creer modifier ou supprimer
     * @throws ControllerException si option incorrect
     * @throws MetierException propagation à la vue
     * @throws DaoException propagation à la vue
     */
    public static void valider (String raisonSocial, String numero, String nomRue, String codePostal, String ville,
                                String email, String telephone, double ca, int nbreEmploye, LocalDate date,
                                String interet, String commentaire, String choix, String option)
            throws ControllerException, MetierException, DaoException {
        switch (option){
            case "creer" :
                creer(raisonSocial, numero, nomRue, codePostal, ville, telephone, email, commentaire,
                        ca, nbreEmploye, date, interet, choix);
                break;
            case "modifier" :
                modifier(raisonSocial, numero, nomRue, codePostal, ville, telephone, email, commentaire,
                        ca, nbreEmploye, date, interet, choix);
                break;
            case "supprimer" :
                supprimer(choix);
                break;
            default:
                LoggerReverso.LOGGER.log(Level.SEVERE, "problème option");
                throw new ControllerException("Erreur, le logiciel va fermer");
        }
    }
}
