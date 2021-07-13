package com.capgemini.customeraccount.enums;

public enum TransactionType {

    CREDIT("credit"),
    DEBIT("debit");

    private final String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
