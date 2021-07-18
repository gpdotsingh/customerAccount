package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.model.TransactionPageModel;
import com.capgemini.customeraccount.services.AccountTransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    AccountTransactionServices accountTransactionServices;

    @GetMapping("/{accountType}/{custId}")
    public TransactionPageModel getAccountTransactions(@RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
                                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize, @PathVariable String accountType, @PathVariable String custId)
    {
        return accountTransactionServices.getAccountTransaction(pageNo,pageSize,custId.trim(),accountType.trim().toUpperCase());
    }

}
