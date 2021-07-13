package com.capgemini.customeraccount.enums;

public enum ExceptionMessage {

    INSSUFFICIENT_BALANCE("Your current balance is "),
    INVALID_ACCOUNT_TYPE("Please correct the account type"),
    CUSTOMER_NOT_FOUND("No custmoer found for customerId: "),
    EXCEPTION_TIME("Time: ");

    private final String  description;

    ExceptionMessage(String description) {
        this.description = description;
    }
}
