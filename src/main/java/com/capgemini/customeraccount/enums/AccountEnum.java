package com.capgemini.customeraccount.enums;


import com.capgemini.customeraccount.exception.AccountException;

import java.util.Arrays;
import java.util.Locale;

public enum AccountEnum {

    CURRENT("current");

    private final String account;

    AccountEnum(String account) {
        this.account = account.toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return  account ;
    }

    public static AccountEnum fromText(String account) {
        return Arrays.stream(values())
                .filter(accountEnum -> accountEnum.account.equalsIgnoreCase(account))
                .findFirst()
                .orElseThrow(()-> new AccountException(ExceptionMessageEnum.INVALID_ACCOUNT_TYPE.name()));
    }
}
