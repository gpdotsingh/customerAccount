package com.capgemini.customeraccount.services;

import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.TransactionType;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.TransactionModel;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface AccountTransactionServices {

    AccountModel createAccount(String custId, AccountEnum accountType,BigDecimal amount);
    AccountModel updateAccount(String custId, TransactionType transactionType, AccountEnum accountType, BigDecimal amount);
    Page<TransactionModel> accountTransaction(int pageNo, int pageSize, AccountEnum accountType,String custId);
}
