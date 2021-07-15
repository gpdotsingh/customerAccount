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
public class AccountController {

    @Autowired
    AccountTransactionServices accountTransactionServices;

    /**
     * Perform account transaction, check account  if exists update it else create new account for existing customer
     * @param amount
     * @param transactionType
     * @param accountType
     * @param custId
     * @return
     */
    @PatchMapping("{accountType}/{custId}")
    public ResponseEntity<AccountModel> updateAccount(@RequestParam BigDecimal amount, @RequestParam TransactionTypeEnum transactionType, @PathVariable AccountEnum accountType, @PathVariable String custId)
    {
        Optional<AccountModel> accountModel = accountTransactionServices.verifyAccount(custId, accountType);

        if(accountModel.isPresent()) {
            return new ResponseEntity(accountTransactionServices.updateAccount(custId, transactionType, accountType, amount, accountModel.get()), HttpStatus.OK);
        }
        return new ResponseEntity(accountTransactionServices.createAccount(custId,accountType,amount,transactionType), HttpStatus.CREATED);
    }

    /**
     *  Responsible to create new account of existing customer
     * @param amount
     * @param transactionType
     * @param accountType
     * @param custId
     * @return
     */
    @PostMapping("{accountType}/{custId}")
    public ResponseEntity<AccountModel> createAccount(@RequestParam BigDecimal amount, @RequestParam TransactionTypeEnum transactionType, @PathVariable AccountEnum accountType, @PathVariable String custId)
    {
        return  new ResponseEntity(accountTransactionServices.createAccount(custId,accountType,amount,transactionType),HttpStatus.CREATED);
    }

}
