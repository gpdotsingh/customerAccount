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
import java.util.Arrays;
import java.util.Optional;

@Service
public class AccountTransactionServicesImpl implements AccountTransactionServices {

    @Autowired
    CurrentAccountDao currentAccountDao;
    @Autowired
    TransactionDao transactionDao;
    @Autowired
    CustomerServices customerServices;

    /**
     * Verify transaction type is credit and account is current only then create
     * @param custId
     * @param accountType
     * @param amount
     * @param transactionTypeEnum
     * @return
     */
    @Override
    public AccountModel createAccount(String custId, String accountType
            , BigDecimal amount,String transactionTypeEnum) {

        if(transactionTypeEnum.equalsIgnoreCase(TransactionTypeEnum.CREDIT.name()))
        {
            switch (AccountEnum.fromText(accountType)) {
                case CURRENT:
                    currentAccountDao.createCurrentAccount(amount, custId);
                    return currentAccountDao.getCurrentAccountEntity(custId)
                            .orElseThrow(() -> new AccountException(ExceptionMessageEnum.TRY_AFTER_SOMETIME.name()));
                default:
                    throw new AccountException(ExceptionMessageEnum.INVALID_ACCOUNT_TYPE.name());
            }
        }
        throw new AccountException(ExceptionMessageEnum.INVALID_ACCOUNT_TYPE.name());
    }

    /**
     * @param custId
     * @param transactionTypeEnum
     * @param accountType
     * @param amount
     * @return
     */
    @Override
    public AccountModel updateAccount(String custId
            , String transactionTypeEnum
            , String accountType, BigDecimal amount
            ,   AccountModel currentAccountOptional) {


        switch (AccountEnum.valueOf(accountType.toUpperCase())) {
            case CURRENT:

                TransactionTypeEnum typeEnum = Arrays.stream(TransactionTypeEnum.values())
                        .filter(transactionType -> transactionType.name().equalsIgnoreCase(transactionTypeEnum))
                        .findFirst()
                        .orElseThrow(()->new GenericException(ExceptionMessageEnum.INVALID_TRANSACTION_TYPE.name()) );

                currentAccountDao.updateCurrentAccount(amount
                        , typeEnum, custId
                        , currentAccountOptional.getAccountNumber());

                return currentAccountDao.getCurrentAccountEntity(custId)
                        .orElseThrow(() -> new AccountException(ExceptionMessageEnum.CUSTOMER_NOT_FOUND.name()));
            default:
                throw new AccountException(ExceptionMessageEnum.INVALID_ACCOUNT_TYPE.name());
        }
        }

    @Override
    public TransactionPageModel getAccountTransaction(int pageNo, int pageSize, String custId, String accountType) {

        Optional<AccountModel> accountModel = verifyAccount(custId, accountType);

        return accountModel.map(model -> transactionDao.getTransaction(pageSize, pageNo, model.getAccountNumber()))
        .orElseThrow(()-> new GenericException(ExceptionMessageEnum.INVALID_ACCOUNT_TYPE.name()));

    }

    @Override
    public Optional<AccountModel> verifyAccount(String custId, String accountType) {

        CustomerModel customerModel = customerServices.getCustomerByCustomerId(custId);

        return customerModel
                .getAccounts()
                .stream()
                .filter(accountModel ->
                        accountModel.getAccountEnum().name()
                                .equalsIgnoreCase(accountType.toUpperCase()))
                .findFirst()
                ;
    }


}
