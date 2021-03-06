package com.capgemini.customeraccount.dao;

import com.capgemini.customeraccount.entity.AccountEntity;
import com.capgemini.customeraccount.enums.TransactionTypeEnum;
import com.capgemini.customeraccount.model.AccountModel;

import java.math.BigDecimal;
import java.util.Optional;

public interface CurrentAccountDao {

    AccountEntity createCurrentAccount(BigDecimal amount, String custId);

    Optional<AccountModel> getCurrentAccountEntity(String custId);

    int updateCurrentAccount(BigDecimal amount,
                             TransactionTypeEnum transactionTypeEnum,
                              String custId, String accountNumber);
}
