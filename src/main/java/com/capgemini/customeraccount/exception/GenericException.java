package com.capgemini.customeraccount.exception;

public class GenericException extends  RuntimeException{

    public GenericException(String exception)
    {
        super(String.format("%s",exception));
    }
}
