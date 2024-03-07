package com.michel.exceptions;

public class ControllerException extends Exception{
    public ControllerException(){

    }

    public ControllerException (String message){
        super((message));
    }
}
