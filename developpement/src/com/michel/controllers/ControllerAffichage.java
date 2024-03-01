package com.michel.controllers;

import com.michel.vues.VueAfficher;

public class ControllerAffichage {

    public static void affichageInit(){
        VueAfficher vueAfficher = new VueAfficher();
    }

    public static void accueil() {
        ControllerAccueil.initAcceuil();
    }
}
