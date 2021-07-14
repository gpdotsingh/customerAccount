package com.capgemini.customeraccount.services;

import com.capgemini.customeraccount.dao.CurrentAccountDao;
import com.capgemini.customeraccount.dao.TransactionDao;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.ExceptionMessageEnum;
import com.capgemini.customeraccount.enums.TransactionTypeEnum;
import com.capgemini.customeraccount.exception.AccountException;
import com.capgemini.customeraccount.exception.GenericException;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.CustomerModel;
import com.capgemini.customeraccount.model.TransactionPageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountTransactionServicesImpl implements AccountTransactionServices {

    @Autowired
    CurrentAccountDao currentAccountDao;
    @Autowired
    TransactionDao transactionDao;
    @Autowired
    CustomerServicesImpl customerServices;

    @Override
    public AccountModel createAccount(String custId, AccountEnum accountType, BigDecimal amount,TransactionTypeEnum transactionTypeEnum) {

        switch (transactionTypeEnum) {
            case DEBIT:
                throw new AccountException(ExceptionMessageEnum.INVALID_TRANSACTION_TYPE.name());
        }
                AccountModel accountModel = new AccountModel();

        switch (accountType) {
            case CURRENT:
                currentAccountDao.createCurrentAccount(amount, custId);
                accountModel = currentAccountDao.getCurrentAccountEntity(custId).get();
        }
        return accountModel;
    }

    /**
     * @param custId
     * @param transactionTypeEnum
     * @param accountType
     * @param amount
     * @return
     */
    @Override
    public AccountModel updateAccount(String custId, TransactionTypeEnum transactionTypeEnum, AccountEnum accountType, BigDecimal amount,   Optional<AccountModel> currentAccountOptional) {

            currentAccountDao.updateCurrentAccount(amount, transactionTypeEnum, custId, currentAccountOptional.get().getAccountNumber());
            return currentAccountDao.getCurrentAccountEntity(custId).get();
    }

    @Override
    public TransactionPageModel getAccountTransaction(int pageNo, int pageSize, String accountNumber) {
        return transactionDao.getTransaction(pageSize, pageNo, accountNumber);

    }

    @Override
    public Optional<AccountModel> verifyAccount(String custId, AccountEnum accountType) {

        CustomerModel customerModel = customerServices.getCustomerByCustomerId(custId);

        return customerModel
                .getAccounts()
                .stream()
                .filter(accountModel -> accountModel.getAccountEnum().equals(accountType))
                .findFirst();
    }


}
