package com.michel.exceptions;

/**
 * erreur lié à la BDD
 */
public class DaoException extends Exception{
    private final int critere;

    /**
     * retourne le niveau de critere
     * @return int critere
     */
    public int getCritere() {
        return critere;
    }

    /**
     * contructeur avec niveau de critique et message
     * @param critere int niveau critique de l'erreur
     * @param message String message à l'utilisateur
     */
    public DaoException(int critere, String message){
        super(message);
        this.critere = critere;
    }
}
