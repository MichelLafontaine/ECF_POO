package com.michel.controllers;

import com.michel.dao.DaoSociete;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.vues.VueAccueil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerAccueil {
    public static void initAcceuil() {
        VueAccueil vueAccueil = new VueAccueil();
    }

    public static ArrayList listeSociete(String choix) throws DaoException {
        return(DaoSociete.raisonSociales(choix));
    }

    public static void creer(String choix) throws SQLException, MetierException, DaoException, ControllerException {
        ControllerFormulaire.formulaireInit(choix, "creer", "");
    }

    public static void modifier(String choix, String raisonSociale) throws MetierException, DaoException, ControllerException {
        ControllerFormulaire.formulaireInit(choix, "modifier", raisonSociale);
    }

    public static void supprimer(String choix, String raisonSociale) throws MetierException, DaoException, ControllerException {
        ControllerFormulaire.formulaireInit(choix, "supprimer", raisonSociale);
    }

    public static void afficher(String choix) throws SQLException, MetierException, DaoException {
        ControllerAffichage.affichageInit(choix);
    }
}
