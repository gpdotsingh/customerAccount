package com.capgemini.customeraccount.dao;

import com.capgemini.customeraccount.entity.AccountEntity;
import com.capgemini.customeraccount.enums.TransactionType;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.TransactionModel;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountTransactionDao {

    AccountEntity createCurrentAccount(BigDecimal amount, String custId);
    int updateCurrentAccount(BigDecimal amount, TransactionType transactionType, String custId);
    Optional<AccountModel> getCurrentAccount(String custId);
    TransactionModel getCurrentTransaction(int pageNo, int pageSize,String custId );
}
