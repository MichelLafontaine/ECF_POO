package com.michel.exceptions;

/**
 * Erreur lié au package metier
 */
public class MetierException extends Exception{
    /**
     * constructeur sans attribut
     */
    public MetierException() {
    }

    /**
     * contructeur avec message
     * @param message String message pour l'utilisateur
     */
    public MetierException(String message){
        super(message);
    }
}
