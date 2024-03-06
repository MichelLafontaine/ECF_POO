package com.michel.controllers;

import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Client;
import com.michel.metiers.Prospect;
import com.michel.utilitaires.Utilitaires;
import com.michel.vues.VueAfficher;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerAffichage {

    private static String choixClientProspect;
    public static void affichageInit(String choix) throws SQLException, MetierException, DaoException {
        VueAfficher vueAfficher = new VueAfficher(choix);
        choixClientProspect = choix;
    }

    public static void accueil() {
        ControllerAccueil.initAcceuil();
    }

    public static  String[][] findAll(String choixClientProspect) throws MetierException, DaoException {
        String[][] listes = new String[0][];
        ArrayList listesObject = null;
        if (choixClientProspect.equals("client")){
            listesObject = DaoClient.findAll();
            listes = new String[listesObject.size()][10];
            for (int i = 0; i < listesObject.size(); i++){
                Client client = (Client) listesObject.get(i);
                listes[i][0] = client.getRaisonSociale();
                listes[i][1] = client.getAdresse().getNumero();
                listes[i][2] = client.getAdresse().getNomRue();
                listes[i][3] = String.valueOf(client.getAdresse().getCodePostal());
                listes[i][4] = client.getAdresse().getVille();
                listes[i][5] = client.getEmail();
                listes[i][6] = client.getTelephone();
                listes[i][7] = String.valueOf(client.getChiffreAffaire());
                listes[i][8] = String.valueOf(client.getNbreEmploye());
                listes[i][9] = client.getCommentaire();
            }
        }
        if (choixClientProspect.equals("prospect")){
            listesObject = DaoProspect.findAll();
            listes = new String[listesObject.size()][10];
            for (int i = 0; i < listesObject.size(); i++){
                Prospect prospect = (Prospect) listesObject.get(i);
                listes[i][0] = prospect.getRaisonSociale();
                listes[i][1] = prospect.getAdresse().getNumero();
                listes[i][2] = prospect.getAdresse().getNomRue();
                listes[i][3] = String.valueOf(prospect.getAdresse().getCodePostal());
                listes[i][4] = prospect.getAdresse().getVille();
                listes[i][5] = prospect.getEmail();
                listes[i][6] = prospect.getTelephone();
                listes[i][7] = prospect.getDateProspect().format(Utilitaires.formatDate());
                String interet = "";
                if (prospect.getInteretProspect() == 1){
                    interet = "oui";
                }
                if (prospect.getInteretProspect() == 0){
                    interet = "non";
                }
                listes[i][8] = interet;
                listes[i][9] = prospect.getCommentaire();
            }
        }

        return listes;
    }
}
