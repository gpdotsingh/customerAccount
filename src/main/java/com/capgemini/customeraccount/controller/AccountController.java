package com.capgemini.customeraccount.controller;

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
    @PutMapping("{accountType}/{custId}")
    public ResponseEntity<AccountModel> updateAccount(@RequestParam(required = false, defaultValue = "0") BigDecimal amount
            , @RequestParam String transactionType
            , @PathVariable String accountType
            , @PathVariable String custId)
    {
        // Check if customer exists return account model else throw exception
        Optional<AccountModel> accountModel = accountTransactionServices.verifyAccount(custId.trim(), accountType.trim().toUpperCase());

        if(accountModel.isPresent()) {
            return new ResponseEntity(accountTransactionServices.updateAccount(custId.trim(), transactionType.trim().toUpperCase()
                    , accountType.trim().toUpperCase()
                    , amount
                    , accountModel.get()), HttpStatus.OK);
        }
        return new ResponseEntity(accountTransactionServices.createAccount(custId.trim().toUpperCase()
                ,accountType.trim().toUpperCase()
                ,amount
                ,transactionType.trim().toUpperCase()), HttpStatus.CREATED);
    }


}
