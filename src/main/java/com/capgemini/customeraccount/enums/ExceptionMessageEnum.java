package com.capgemini.customeraccount.enums;

public enum ExceptionMessageEnum  {

    INVALID_TRANSACTION_TYPE("INVALID_TRANSACTION"),
    TRY_AFTER_SOMETIME2("TRY_AFTER_SOMETIME2"),
    TRY_AFTER_SOMETIME("TRY_AFTER_SOMETIME"),
    INSSUFFICIENT_BALANCE("INSSUFFICIENT_BALANCE"),
    INVALID_ACCOUNT_TYPE("INVALID_ACCOUNT_TYPE"),
    CUSTOMER_NOT_FOUND("CUSTOMER_NOT_FOUND"),
    ERROR("ERROR"),
    KINDLY_VERIFY_CUSTOMER("KINDLY_VERIFY_CUSTOMER"),
    EXCEPTION_TIME("EXCEPTION_TIME");

    private String exceptionMessage;

    ExceptionMessageEnum(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
