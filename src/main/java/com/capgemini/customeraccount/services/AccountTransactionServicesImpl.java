package com.capgemini.customeraccount.services;

import com.capgemini.customeraccount.dao.AccountTransactionDao;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.TransactionType;
import com.capgemini.customeraccount.exception.AccountException;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountTransactionServicesImpl implements AccountTransactionServices {

    @Autowired
    AccountTransactionDao accountTransactionDao;

    @Override
    public AccountModel createAccount(String custId, AccountEnum accountType, BigDecimal amount) {
        return null;
    }

    @Override
    public AccountModel updateAccount(String custId, TransactionType transactionType, AccountEnum accountType, BigDecimal amount) {
    //Change to switch
        if (accountType == AccountEnum.CURRENT && amount.compareTo(BigDecimal.ZERO)>=0) {
            int updateStatus = accountTransactionDao.updateCurrentAccount(amount, transactionType, custId);
            return accountTransactionDao.getCurrentAccount(custId).get();
        }
        throw new AccountException(transactionType.name());
    }

    @Override
    public Page<TransactionModel> accountTransaction(int pageNo, int pageSize, AccountEnum accountType, String custId) {
        return null;
    }
}
