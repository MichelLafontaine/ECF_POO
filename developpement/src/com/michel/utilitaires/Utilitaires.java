package com.michel.utilitaires;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        return DateTimeFormatter.ofPattern("dd MMMM yyyy");
    }
}
