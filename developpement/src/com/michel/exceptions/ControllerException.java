package com.michel.exceptions;

/**
 * en cas d'erreur dans le package Controller
 */
public class ControllerException extends Exception{
    /**
     * constructeur sans attribut
     */
    public ControllerException(){

    }

    /**
     * constructeur avec message
     * @param message String message pour l'utilisateur
     */
    public ControllerException (String message){
        super((message));
    }
}
