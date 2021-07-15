package com.capgemini.customeraccount.services;

import com.capgemini.customeraccount.dao.CurrentAccountDao;
import com.capgemini.customeraccount.dao.TransactionDao;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.ExceptionMessageEnum;
import com.capgemini.customeraccount.enums.TransactionTypeEnum;
import com.capgemini.customeraccount.exception.AccountException;
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
    CustomerServices customerServices;

    @Override
    public AccountModel createAccount(String custId, AccountEnum accountType, BigDecimal amount,TransactionTypeEnum transactionTypeEnum) {

        if (transactionTypeEnum == TransactionTypeEnum.DEBIT) {
                throw new AccountException(ExceptionMessageEnum.INVALID_TRANSACTION_TYPE.name());
        }

        customerServices.getCustomerByCustomerId(custId);

        switch (accountType) {
            case CURRENT:
                currentAccountDao.createCurrentAccount(amount, custId);
                return currentAccountDao.getCurrentAccountEntity(custId).orElseThrow(()->new AccountException(ExceptionMessageEnum.TRY_AFTER_SOMETIME.name()));
            default:
                throw new AccountException(ExceptionMessageEnum.INVALID_ACCOUNT_TYPE.name());
        }
    }

    /**
     * @param custId
     * @param transactionTypeEnum
     * @param accountType
     * @param amount
     * @return
     */
    @Override
    public AccountModel updateAccount(String custId, TransactionTypeEnum transactionTypeEnum, AccountEnum accountType, BigDecimal amount,   AccountModel currentAccountOptional) {

        switch (accountType) {
            case CURRENT:
                currentAccountDao.updateCurrentAccount(amount, transactionTypeEnum, custId, currentAccountOptional.getAccountNumber());
                return currentAccountDao.getCurrentAccountEntity(custId).orElseThrow(() -> new AccountException(ExceptionMessageEnum.CUSTOMER_NOT_FOUND.name()));
            default:
                throw new AccountException(ExceptionMessageEnum.INVALID_ACCOUNT_TYPE.name());
        }
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
