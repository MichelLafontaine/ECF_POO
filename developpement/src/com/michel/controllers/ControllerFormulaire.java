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
import com.michel.utilitaires.Utilitaires;
import com.michel.vues.VueFormulaire;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;

public class ControllerFormulaire {

    private static int identifiant;

    public static void formulaireInit(String choixClientProspect, String option, String raisonSociale) throws MetierException, DaoException, ControllerException {
        VueFormulaire vueFormulaire = new VueFormulaire(choixClientProspect, option);
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
                vueFormulaire.settNbreEmploye(client.getNbreEmploye());
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
                vueFormulaire.setjComboBoxAnnee(prospect.getDateProspect().getYear());
                System.out.println(prospect.getDateProspect().getDayOfMonth());
                switch (prospect.getDateProspect().getMonthValue()){
                    case 1 :
                        vueFormulaire.setjComboBoxMois("Janvier");
                        break;
                    case 2 :
                        vueFormulaire.setjComboBoxMois("Février");
                        break;
                    case 3 :
                        vueFormulaire.setjComboBoxMois("Mars");
                        break;
                    case 4 :
                        vueFormulaire.setjComboBoxMois("Avril");
                        break;
                    case 5 :
                        vueFormulaire.setjComboBoxMois("Mai");
                        break;
                    case 6 :
                        vueFormulaire.setjComboBoxMois("Juin");
                        break;
                    case 7 :
                        vueFormulaire.setjComboBoxMois("Juillet");
                        break;
                    case 8 :
                        vueFormulaire.setjComboBoxMois("Août");
                        break;
                    case 9 :
                        vueFormulaire.setjComboBoxMois("Septembre");
                        break;
                    case 10 :
                        vueFormulaire.setjComboBoxMois("Octobre");
                        break;
                    case 11 :
                        vueFormulaire.setjComboBoxMois("Novembre");
                        break;
                    case 12 :
                        vueFormulaire.setjComboBoxMois("Décembre");
                        break;
                    default:
                        LoggerReverso.LOGGER.log(Level.SEVERE, "problème choix mois");
                        throw new ControllerException("Erreur, le logiciel va fermer");
                }

                identifiant = prospect.getIdentifiant();
            } else {
                LoggerReverso.LOGGER.log(Level.SEVERE, "problème choix client/prospect");
                throw new ControllerException("Erreur, le logiciel va fermer");
            }
            vueFormulaire.setRaisonSociale(raisonSociale);

        }
    }

    public static ArrayList<String> listeYears(){
        return Utilitaires.years();
    }

    public static void creer(String raisonSociale, String numero, String nomRue, int codePostal, String ville,
                             String telephone, String email, String commentaire, double ca,
                             int nbreEmploye, LocalDate dateProspect, String interet, String choix)
            throws MetierException, SQLException, DaoException, ControllerException {
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

    public static void modifier(String raisonSociale, String numero, String nomRue, int codePostal, String ville,
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
}
