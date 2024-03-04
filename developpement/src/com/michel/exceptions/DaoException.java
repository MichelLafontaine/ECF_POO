package com.michel.exceptions;

public class DaoException extends Exception{

    public DaoException() {
    }

    public DaoException(int critere, String message){
        super(message);
        if (critere == 2){
            System.exit(1);
        }
    }
}
