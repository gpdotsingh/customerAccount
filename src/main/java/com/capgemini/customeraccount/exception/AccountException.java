package com.capgemini.customeraccount.exception;

public class AccountException extends RuntimeException {
    public AccountException(String exception) {
        super(String.format("%s ",exception));
    }
}