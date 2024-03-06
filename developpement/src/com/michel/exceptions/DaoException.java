package com.michel.exceptions;

public class DaoException extends Exception{
    private int critere;

    public int getCritere() {
        return critere;
    }

    public DaoException() {
    }

    public DaoException(int critere, String message){
        super(message);
        this.critere = critere;
    }
}
