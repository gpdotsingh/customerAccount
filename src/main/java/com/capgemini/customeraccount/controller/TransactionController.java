package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.model.TransactionModel;
import com.capgemini.customeraccount.services.AccountTransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class TransactionController {

    @Autowired
    AccountTransactionServices accountTransactionServices;

    @GetMapping("/transactions/{accountType}/{custId}")
    public Page<TransactionModel> transactions(@RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize, AccountEnum accountType,String custId)

    {
        return accountTransactionServices.accountTransaction(pageNo,pageSize, accountType,custId);
    }

}
