package com.capgemini.customeraccount.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String exception) {
        super(String.format("%s ",exception));
    }
}
