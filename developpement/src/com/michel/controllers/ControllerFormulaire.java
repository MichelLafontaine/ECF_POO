package com.michel.controllers;

import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Client;
import com.michel.metiers.Prospect;
import com.michel.utilitaires.Utilitaires;
import com.michel.vues.VueFormulaire;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerFormulaire {

    private static int identifiant;
    private String raisonSociale;
    private String email;
    private String telephone;
    private String commentaire;
    private Adresse adresse;
    private LocalDate dateProspect;
    private int interetProspect = -1;
    private double chiffreAffaire;
    private int nbreEmploye;

    public static void formulaireInit(String choixClientProspect, String option, String raisonSociale) throws SQLException, MetierException, DaoException {
        VueFormulaire vueFormulaire = new VueFormulaire(choixClientProspect, option);
        if (option.equals("modifier") || option.equals("supprimer")){
            if (choixClientProspect.equals("client")){
                Client client = DaoClient.findByNameClient(raisonSociale);
                vueFormulaire.setNumero(client.getAdresse().getNumero());
                vueFormulaire.setNomRue(client.getAdresse().getNomRue());
                vueFormulaire.setCodePostal(client.getAdresse().getCodePostal());
                vueFormulaire.setVille(client.getAdresse().getVille());
                vueFormulaire.setEmail(client.getEmail());
                vueFormulaire.setTelephone(client.getTelephone());
                vueFormulaire.setCA(client.getChiffreAffaire());
                vueFormulaire.settNbreEmploye(client.getNbreEmploye());
                identifiant = client.getIdentifiant();
            }
            if (choixClientProspect.equals("prospect")){
                Prospect prospect = DaoProspect.findByNameClient(raisonSociale);
                vueFormulaire.setNumero(prospect.getAdresse().getNumero());
                vueFormulaire.setNomRue(prospect.getAdresse().getNomRue());
                vueFormulaire.setCodePostal(prospect.getAdresse().getCodePostal());
                vueFormulaire.setVille(prospect.getAdresse().getVille());
                vueFormulaire.setEmail(prospect.getEmail());
                vueFormulaire.setTelephone(prospect.getTelephone());
                if (prospect.getInteretProspect() == 1){
                    vueFormulaire.setInteret("oui");
                } else {
                    vueFormulaire.setInteret("non");
                }
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
                        vueFormulaire.setjComboBoxMois("");
                }

                identifiant = prospect.getIdentifiant();
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
            throws MetierException, SQLException, DaoException {
        Adresse adresse = new Adresse(numero, nomRue, ville, codePostal);
        int interetInt = -1;
        if (choix.equals("client")){
            Client client = new Client(raisonSociale, email, telephone, commentaire, adresse, ca, nbreEmploye);
            DaoClient.createClient(client);
        }
        if (choix.equals("prospect")){
            if (interet.equals("oui")){
                interetInt = 1;
            }
            if (interet.equals("non")) {
                interetInt = 0;
            }
            Prospect prospect = new Prospect(raisonSociale, email, telephone, commentaire,
                    adresse, dateProspect, interetInt);
            DaoProspect.createProspect(prospect);
        }

    }

    public static void modifier(String raisonSociale, String numero, String nomRue, int codePostal, String ville,
                                String telephone, String email, String commentaire, double ca,
                                int nbreEmploye, LocalDate dateProspect, String interet, String choix)
            throws MetierException, DaoException {
        Adresse adresse = new Adresse(numero, nomRue, ville, codePostal);
        int interetInt = -1;

        if (choix.equals("client")){
            Client client = new Client(identifiant, raisonSociale, email, telephone, commentaire, adresse, ca, nbreEmploye);
            DaoClient.updateCLient(client, identifiant);
        }
        if (choix.equals("prospect")){
            if (interet.equals("oui")){
                interetInt = 1;
            }
            if (interet.equals("non")) {
                interetInt = 0;
            }
            Prospect prospect = new Prospect(identifiant, raisonSociale, email, telephone, commentaire,
                    adresse, dateProspect, interetInt);
            DaoProspect.updatePropspect(prospect, identifiant);
        }

    }

    public static void supprimer(String raisonSociale, String numero, String nomRue, int codePostal, String ville,
                                 String telephone, String email, String commentaire, double ca,
                                 int nbreEmploye, LocalDate dateProspect, String interet, String choix) {
    }
}
