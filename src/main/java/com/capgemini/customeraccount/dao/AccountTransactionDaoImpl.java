package com.capgemini.customeraccount.dao;

import com.capgemini.customeraccount.entity.AccountEntity;
import com.capgemini.customeraccount.entity.CurrentAccountEntity;
import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.TransactionType;
import com.capgemini.customeraccount.exception.GenericException;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.TransactionModel;
import com.capgemini.customeraccount.repository.AccountRepo;
import com.capgemini.customeraccount.repository.CurrentAcountRepo;
import com.capgemini.customeraccount.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class AccountTransactionDaoImpl implements AccountTransactionDao {


    private Logger logger = LoggerFactory.getLogger(AccountTransactionDaoImpl.class);

    @Autowired
    CurrentAcountRepo currentAcountRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    CustomerRepo customerRepo;

    Optional<AccountModel> accountModelOptional = Optional.of(new AccountModel());

    @Override
    public AccountEntity createCurrentAccount(BigDecimal amount, String custId) {
        AccountEntity accountEntitySaved =null;
        try {
            // Get customer
            Optional<CustomerEntity> customer = customerRepo.findByCustomerId(custId);
            // Create account
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setAccountEnum(AccountEnum.CURRENT);
            accountEntity.setCustomer(customer.get());
            // Create current account
            CurrentAccountEntity currentAccountEntity = new CurrentAccountEntity();
            currentAccountEntity.setCustomerId(custId);
            currentAccountEntity.setLastUpdatedTime(LocalDateTime.now());
            currentAccountEntity.setAccountNumber(custId);
            currentAccountEntity.setAccountEntity(accountEntity);
            currentAccountEntity.setAmount(amount);
            currentAccountEntity.setAccountCreationTime(LocalDateTime.now());

            accountEntity.setCurrentAccountEntity(currentAccountEntity);
            accountEntitySaved = accountRepo.save(accountEntity);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            throw  new GenericException("Please check db");
        }
        return accountEntitySaved;
    }

    @Override
    @Transactional
    public int  updateCurrentAccount(BigDecimal amount, TransactionType transactionType, String custId) {
        // Check account exists
        Optional<CurrentAccountEntity> currentAccountEntity = currentAcountRepo.findByCustomerId(custId);
        int updateStatus = 0;
        // If not create account
        if ((!currentAccountEntity.isPresent())
                && transactionType==TransactionType.CREDIT) {
            updateStatus =  null!=createCurrentAccount(amount, custId)? 1:0  ;
        }
        // account is curent account but type is debit
        else if((!currentAccountEntity.isPresent())){
            logger.info(String.format("your customer Id is : %s",custId));
            throw new GenericException("You don't have account");
        }
        // credit/debit amount
        else {
            AccountModel accountModel = entityToModel(currentAccountEntity.get());
            updateStatus = creditDebitOperation(amount, transactionType, custId,accountModel);
        }
        return updateStatus;
    }

    @Override
    public Optional<AccountModel> getCurrentAccount(String custId) {

        Optional<CurrentAccountEntity> currentAccountEntity = currentAcountRepo.findByCustomerId(custId);
        // if account is present
        if (currentAccountEntity.isPresent()) {
            CurrentAccountEntity accountEntity = currentAccountEntity.get();
            AccountModel accountModel = entityToModel(accountEntity);
            accountModelOptional = Optional.of(accountModel);
        }
        return accountModelOptional;
    }

    private int creditDebitOperation(BigDecimal amount, TransactionType transactionType, String custId, AccountModel currentAccountInfo) {
        int updateStatus = 0;
        if (transactionType == TransactionType.CREDIT) {
            updateStatus = currentAcountRepo.updateByCustId(custId, amount, LocalDateTime.now());
        }
        // debit the amount
        else if (transactionType == TransactionType.DEBIT) {
            BigDecimal currBalance = currentAccountInfo.getAmount();
            try {
                updateStatus = currentAcountRepo.updateByCustIdDecrement(custId, amount, LocalDateTime.now());
            } catch (StackOverflowError stackOverflowError) {
                logger.error(stackOverflowError.getMessage());
                throw new GenericException(String.valueOf(currBalance));
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new GenericException(String.valueOf(currBalance));
            }
        }

        return updateStatus;
    }

    private AccountModel entityToModel(CurrentAccountEntity accountEntity) {
        AccountModel accountModel = new AccountModel(AccountEnum.CURRENT, accountEntity.getAmount(), accountEntity.getAccountCreationTime(), accountEntity.getLastUpdatedTime(), null);
        return accountModel;
    }

    @Override
    public TransactionModel getCurrentTransaction(int pageNo, int pageSize, String custId) {
        return null;
    }

}
