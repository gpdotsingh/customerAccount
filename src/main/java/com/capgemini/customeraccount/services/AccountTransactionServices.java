package com.capgemini.customeraccount.services;

import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.TransactionTypeEnum;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.TransactionPageModel;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

public interface AccountTransactionServices {

    AccountModel createAccount(String custId, AccountEnum accountType,BigDecimal amount,TransactionTypeEnum transactionTypeEnum);
    AccountModel updateAccount(String custId, TransactionTypeEnum transactionTypeEnum, AccountEnum accountType, BigDecimal amount,AccountModel currentAccountOptional);
    TransactionPageModel getAccountTransaction(int pageNo, int pageSize, String accountNumber);
    Optional<AccountModel> verifyAccount(String custId,AccountEnum accountType);
}
