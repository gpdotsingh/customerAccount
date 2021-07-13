package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.entity.AccountEntity;
import com.capgemini.customeraccount.entity.CurrentAccountEntity;
import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.TransactionType;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.repository.AccountRepo;
import com.capgemini.customeraccount.repository.CurrentAcountRepo;
import com.capgemini.customeraccount.repository.CustomerRepo;
import com.capgemini.customeraccount.services.AccountTransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class CurrentAccountController {

    @Autowired
    CurrentAcountRepo currentAcountRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    AccountTransactionServices accountTransactionServices;

    @GetMapping("/{custId}")
    public String createAccount(@PathVariable String custId)
    {
        Optional<CustomerEntity> customer = customerRepo.findByCustomerId(custId);
        if(customer.isPresent())
        {

            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setAccountEnum(AccountEnum.CURRENT);
            accountEntity.setCustomer(customer.get());

            CurrentAccountEntity currentAccountEntity = new CurrentAccountEntity();
            currentAccountEntity.setCustomerId(custId);
            currentAccountEntity.setLastUpdatedTime(LocalDateTime.now());
            currentAccountEntity.setAccountNumber(custId);
            currentAccountEntity.setAccountEntity(accountEntity);
            currentAccountEntity.setAmount(BigDecimal.valueOf(0));
            currentAccountEntity.setAccountCreationTime(LocalDateTime.now());
            accountEntity.setCurrentAccountEntity(currentAccountEntity);

            accountRepo.save(accountEntity);

        }

        return "Success";
    }

    @PutMapping("/{custId}")
    public String updateAccount(@PathVariable String custId)
    {
        Optional<CurrentAccountEntity> currentAccountEntity = currentAcountRepo.findByCustomerId(custId);

        if(currentAccountEntity.isPresent())
        {
            currentAccountEntity.get().setAmount(BigDecimal.valueOf(28));
            currentAcountRepo.save(currentAccountEntity.get());
        }
        return "Success";
    }


    @PutMapping("{accountType}/{custId}")
    public AccountModel updateAccount(@RequestParam BigDecimal amount, @RequestParam TransactionType transactionType, @PathVariable AccountEnum accountType, @PathVariable String custId)
    {
        return accountTransactionServices.updateAccount(custId,transactionType,accountType,amount);
    }
}
