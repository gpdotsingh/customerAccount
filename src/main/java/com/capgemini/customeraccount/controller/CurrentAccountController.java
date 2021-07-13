package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.TransactionType;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.services.AccountTransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
public class CurrentAccountController {

    @Autowired
    AccountTransactionServices accountTransactionServices;

    @PutMapping("{accountType}/{custId}")
    public AccountModel updateAccount(@RequestParam BigDecimal amount, @RequestParam TransactionType transactionType, @PathVariable AccountEnum accountType, @PathVariable String custId)
    {
        return accountTransactionServices.updateAccount(custId,transactionType,accountType,amount);
    }

    @PostMapping("{accountType}/{custId}")
    public AccountModel createAccount(@RequestParam BigDecimal amount, @PathVariable AccountEnum accountType, @PathVariable String custId)
    {
        return accountTransactionServices.createAccount(custId,accountType,amount);
    }

}
