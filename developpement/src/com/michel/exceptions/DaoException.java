package com.michel.exceptions;

public class DaoException extends Exception{

    public DaoException() {
    }

    public DaoException(String message){
        super(message);
    }
}
