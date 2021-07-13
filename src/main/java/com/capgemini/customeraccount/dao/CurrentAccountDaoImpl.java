package com.capgemini.customeraccount.dao;

import com.capgemini.customeraccount.dto.AccountTransactionDto;
import com.capgemini.customeraccount.entity.AccountEntity;
import com.capgemini.customeraccount.entity.CurrentAccountEntity;
import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.ExceptionMessage;
import com.capgemini.customeraccount.enums.TransactionType;
import com.capgemini.customeraccount.exception.GenericException;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.repository.AccountRepo;
import com.capgemini.customeraccount.repository.CurrentAcountRepo;
import com.capgemini.customeraccount.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class CurrentAccountDaoImpl implements CurrentAccountDao {


    private Logger logger = LoggerFactory.getLogger(CurrentAccountDaoImpl.class);

    @Autowired
    CurrentAcountRepo currentAcountRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    AccountTransactionDto accountTransactionDto;

    @Override
    public AccountEntity createCurrentAccount(BigDecimal amount, String custId) {
        AccountEntity accountEntitySaved = null;
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
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GenericException("Please check db");
        }
        return accountEntitySaved;
    }

    @Override
    public int updateCurrentAccount(BigDecimal amount, TransactionType transactionType, String custId) {
        int updateStatus = - 1;
        try {
            if (transactionType == TransactionType.CREDIT) {
                updateStatus = currentAcountRepo.updateByCustId(custId, amount, LocalDateTime.now());
            }
            else if (transactionType == TransactionType.DEBIT) {
                updateStatus = currentAcountRepo.updateByCustIdDecrement(custId, amount, LocalDateTime.now()); }
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException) {
            logger.error(dataIntegrityViolationException.getMessage());
            throw new GenericException(ExceptionMessage.INSSUFFICIENT_BALANCE.name());
        }

        catch (Exception e) {
            logger.error(e.getMessage());
            throw new GenericException(ExceptionMessage.TRY_AFTER_SOMETIME.name());
        }
        return updateStatus;
    }

    @Override
    public Optional<AccountModel> getCurrentAccountEntity(String custId) {
        // Check account exists
        Optional<CurrentAccountEntity> customerInfo = currentAcountRepo.findByCustomerId(custId);
        return accountTransactionDto.getCurrentAccount(customerInfo);
    }

}
