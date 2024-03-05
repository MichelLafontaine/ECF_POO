package com.michel.utilitaires;

import com.michel.exceptions.MetierException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Utilitaires {

    // vérification de l'adresse mail
    public static final Pattern PATTERN_MAIL =
            Pattern.compile ("(\\S.*\\S)(@)(\\S.*\\S)(.\\S[a-z]{2,3})");

    // Numéro de téléphone français
    public static final Pattern PATTERN_TEL =
            Pattern.compile("(0|\\+33|0033)[1-9][0-9]{8}");

    //Code Postal français doit comporter 5 chiffres
    public static final Pattern PATTERN_CODEPOSTAL =
            Pattern.compile("\\d{5}");

    //format date Local
    public static DateTimeFormatter formatDate (){
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public static ArrayList<String> years (){
        ArrayList<String> listAnnee = new ArrayList<>();
        listAnnee.add("");
        int anneeActuelle = LocalDate.now().getYear();

        for (int annee = 2020; annee <= anneeActuelle ; annee++){
            listAnnee.add(String.valueOf(annee));
        }

        return listAnnee;
    }

    public static int mois (String mois){
        switch (mois){
            case "Janvier" :
                return 1;
            case "Février" :
                return 2;
            case "Mars" :
                return 3;
            case "Avril" :
                return 4;
            case "Mai" :
                return 5;
            case "Juin" :
                return 6;
            case "Juillet" :
                return 7;
            case "Août" :
                return 8;
            case "Septembre" :
                return 9;
            case "Octobre" :
                return 10;
            case "Novembre" :
                return 11;
            case "Décembre" :
                return 12;
            default:
                LoggerReverso.LOGGER.log(Level.SEVERE, "problème lecture mois");
                return 0;
        }
    }
}
