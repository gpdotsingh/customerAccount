package com.capgemini.customeraccount.enums;

public enum ExceptionMessage {

    TRY_AFTER_SOMETIME2("Transaction services are down please try after some time"),
    TRY_AFTER_SOMETIME("Your current balance is "),
    INSSUFFICIENT_BALANCE("Your current balance is "),
    INVALID_ACCOUNT_TYPE("Please correct the account type"),
    CUSTOMER_NOT_FOUND("No custmoer found for customerId: "),
    EXCEPTION_TIME("Time: ");

    private final String  description;

    ExceptionMessage(String description) {
        this.description = description;
    }
}
