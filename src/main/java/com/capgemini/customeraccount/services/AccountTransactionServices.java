package com.capgemini.customeraccount.services;

import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.TransactionType;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.TransactionPageModel;

import java.math.BigDecimal;

public interface AccountTransactionServices {

    AccountModel createAccount(String custId, AccountEnum accountType,BigDecimal amount);
    AccountModel updateAccount(String custId, TransactionType transactionType, AccountEnum accountType, BigDecimal amount);
    TransactionPageModel getAccountTransaction(int pageNo, int pageSize, String accountNumber);

}
