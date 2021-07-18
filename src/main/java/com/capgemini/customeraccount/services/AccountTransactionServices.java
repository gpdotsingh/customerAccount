package com.capgemini.customeraccount.services;

import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.TransactionPageModel;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountTransactionServices {

    AccountModel createAccount(String custId, String accountType
            ,BigDecimal amount,String transactionTypeEnum);

    AccountModel updateAccount(String custId
            , String transactionTypeEnum
            , String accountType, BigDecimal amount
            ,AccountModel currentAccountOptional);

    TransactionPageModel getAccountTransaction(int pageNo, int pageSize, String custId, String accountType);

    Optional<AccountModel> verifyAccount(String custId,String accountType);

}
