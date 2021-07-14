package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.TransactionTypeEnum;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.services.AccountTransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class CurrentAccountController {

    @Autowired
    AccountTransactionServices accountTransactionServices;

    /**
     * Check account
     *  if exists update it else create it
     * @param amount
     * @param transactionTypeEnum
     * @param accountType
     * @param custId
     * @return
     */
    @PatchMapping("{accountType}/{custId}")
    public ResponseEntity<AccountModel> updateAccount(@RequestParam BigDecimal amount, @RequestParam TransactionTypeEnum transactionTypeEnum, @PathVariable AccountEnum accountType, @PathVariable String custId)
    {
        Optional<AccountModel> accountModel = accountTransactionServices.verifyAccount(custId, accountType);

        if(accountModel.isPresent())
            return new ResponseEntity(accountTransactionServices.updateAccount(custId, transactionTypeEnum,accountType,amount,accountModel), HttpStatus.OK);

        return new ResponseEntity(accountTransactionServices.createAccount(custId,accountType,amount,transactionTypeEnum), HttpStatus.CREATED);
    }

    @PostMapping("{accountType}/{custId}")
    public ResponseEntity<AccountModel> createAccount(@RequestParam BigDecimal amount, @RequestParam TransactionTypeEnum transactionTypeEnum, @PathVariable AccountEnum accountType, @PathVariable String custId)
    {
        return  new ResponseEntity(accountTransactionServices.createAccount(custId,accountType,amount,transactionTypeEnum),HttpStatus.CREATED);
    }

    @GetMapping("{accountType}/{custId}")
    public ResponseEntity<AccountModel> delete(@RequestParam BigDecimal amount, @RequestParam TransactionTypeEnum transactionTypeEnum, @PathVariable AccountEnum accountType, @PathVariable String custId)
    {
        return  new ResponseEntity(accountTransactionServices.createAccount(custId,accountType,amount,transactionTypeEnum),HttpStatus.CREATED);
    }

}
