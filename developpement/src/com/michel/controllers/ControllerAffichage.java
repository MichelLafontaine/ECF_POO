package com.michel.controllers;

import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Client;
import com.michel.metiers.Prospect;
import com.michel.utilitaires.LoggerReverso;
import com.michel.utilitaires.Utilitaires;
import com.michel.vues.VueAfficher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

public class ControllerAffichage {

    /**
     * affichageinit
     * @param choix
     * @throws MetierException
     * @throws DaoException
     * @throws ControllerException
     */
    public static void affichageInit(String choix) throws MetierException, DaoException, ControllerException {
        VueAfficher vueAfficher = new VueAfficher(choix);
    }

    /**
     * accueil
     */
    public static void accueil() {
        ControllerAccueil.initAcceuil();
    }

    /**
     * finAll
     * @param choix
     * @return
     * @throws MetierException
     * @throws DaoException
     * @throws ControllerException
     */
    public static String[][] findAll(String choix) throws MetierException, DaoException, ControllerException {
        String[][] listes = new String[0][];
        ArrayList listesObject = null;
        if (choix.equals("client")){
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
        } else if (choix.equals("prospect")){
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
        } else {
            LoggerReverso.LOGGER.log(Level.SEVERE, "erreur option");
            throw new ControllerException("Erreur logiciel");
        }
        return listes;
    }

    /**
     * nomColonne
     * @param choix
     * @return
     * @throws ControllerException
     */
    public static String[] nomColonne(String choix) throws ControllerException {
        if (choix.equals("client")) {
            return new String[]{"Raison Sociale", "N°", "Nom de rue", "CP", "Ville", "Email", "Téléphone", "CA",
                    "Nbre d'employé", "commentaire"};
        } else if (choix.equals("prospect")) {
            return new String[]{"Raison Sociale", "N°", "Nom de rue", "CP", "Ville", "Email", "Téléphone",
                    "Date Prospection", "Intérêt", "commentaire"};
        } else {
            LoggerReverso.LOGGER.log(Level.SEVERE, "erreur option");
            throw new ControllerException("Erreur logiciel");
        }
    }
}
