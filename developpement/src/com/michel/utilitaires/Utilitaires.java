package com.michel.utilitaires;

import com.michel.exceptions.MetierException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Utilitaires {
    /**
     * pattern mail
     */

    // vérification de l'adresse mail
    public static final Pattern PATTERN_MAIL =
            Pattern.compile ("(\\S.*\\S)(@)(\\S.*\\S)(.\\S[a-z]{2,3})");

    /**
     * pattern telephone
     */
    // Numéro de téléphone français
    public static final Pattern PATTERN_TEL =
            Pattern.compile("(0|\\+33|0033)[1-9][0-9]{8}");

    /**
     * pattern code postal
     */
    //Code Postal français doit comporter 5 chiffres
    public static final Pattern PATTERN_CODEPOSTAL =
            Pattern.compile("\\d{5}");

    /**
     * format Date dd/MM/yyyy
     * @return
     */
    //format date Local
    public static DateTimeFormatter formatDate (){
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    /**
     * liste des années depuis 2000
     * @return
     */
    public static ArrayList<String> years (){
        ArrayList<String> listAnnee = new ArrayList<>();
        listAnnee.add("années");
        int anneeActuelle = LocalDate.now().getYear();

        for (int annee = 2000; annee <= anneeActuelle ; annee++){
            listAnnee.add(String.valueOf(annee));
        }
        return listAnnee;
    }
}
