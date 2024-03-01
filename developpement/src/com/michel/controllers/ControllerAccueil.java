package com.michel.controllers;

import com.michel.dao.DaoSociete;
import com.michel.vues.VueAccueil;

import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerAccueil {
    public static void initAcceuil() {
        VueAccueil vueAccueil = new VueAccueil();
    }

    public static ArrayList listeSociete(String choix) throws SQLException {
        return(DaoSociete.raisonSociales(choix));
    }

    public static void creer(String choix){
        ControllerFormulaire.formulaireInit();
    }

    public static void modifier(String choix) {
        ControllerFormulaire.formulaireInit();
    }

    public static void supprimer(String choix) {
        ControllerFormulaire.formulaireInit();
    }

    public static void afficher(String choix) {
        ControllerAffichage.affichageInit();
    }
}
