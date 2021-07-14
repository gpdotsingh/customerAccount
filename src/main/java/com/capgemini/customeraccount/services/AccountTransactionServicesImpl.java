package com.capgemini.customeraccount.services;

import com.capgemini.customeraccount.dao.CurrentAccountDao;
import com.capgemini.customeraccount.dao.TransactionDao;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.TransactionType;
import com.capgemini.customeraccount.exception.AccountException;
import com.capgemini.customeraccount.exception.GenericException;
import com.capgemini.customeraccount.model.AccountModel;
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

    @Override
    public AccountModel createAccount(String custId, AccountEnum accountType, BigDecimal amount) {
        AccountModel accountModel = new AccountModel();
        if (accountType == AccountEnum.CURRENT) {
            currentAccountDao.createCurrentAccount(amount, custId);
            accountModel = currentAccountDao.getCurrentAccountEntity(custId).get();
        }
        return accountModel;
    }

    /**
     * @param custId
     * @param transactionType
     * @param accountType
     * @param amount
     * @return
     */
    @Override
    public AccountModel updateAccount(String custId, TransactionType transactionType, AccountEnum accountType, BigDecimal amount) {
        //if account type is current account
        if (accountType == AccountEnum.CURRENT && amount.compareTo(BigDecimal.ZERO) >= 0) {
            Optional<AccountModel> currentAccountOptional
                    = currentAccountDao.getCurrentAccountEntity(custId);
            // if account exist update transaction else if transaction is CREDIT open new account else throw exception
            if (currentAccountOptional.isPresent()) {
                currentAccountDao.updateCurrentAccount(amount, transactionType, custId,currentAccountOptional.get().getAccountNumber());

            } else if (transactionType == TransactionType.CREDIT) {
                currentAccountDao.createCurrentAccount(amount, custId);
            } else {
                throw new GenericException("Please open current account first");
            }
            return currentAccountDao.getCurrentAccountEntity(custId).get();
        }

        throw new AccountException(transactionType.name());
    }

    @Override
    public TransactionPageModel getAccountTransaction(int pageNo, int pageSize, String accountNumber) {
        return transactionDao.getTransaction(pageSize, pageNo, accountNumber);

    }


}
