package com.capgemini.customeraccount.enums;

public enum TransactionTypeEnum {

    CREDIT("credit"),
    DEBIT("debit");

    private final String type;

    TransactionTypeEnum(String type) {
        this.type = type;
    }
}
