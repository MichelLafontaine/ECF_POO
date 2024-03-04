package com.michel.controllers;

import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.vues.VueAfficher;

import java.sql.SQLException;

public class ControllerAffichage {

    public static void affichageInit() throws SQLException, MetierException, DaoException {
        VueAfficher vueAfficher = new VueAfficher();
    }

    public static void accueil() throws SQLException, MetierException, DaoException {
        ControllerAccueil.initAcceuil();
    }
}
