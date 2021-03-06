package com.capgemini.customeraccount.dao;

import com.capgemini.customeraccount.model.TransactionPageModel;

public interface TransactionDao {

    TransactionPageModel getTransaction(int pageSize, int pageNo, String accountNumber);

    void logTransaction(String amount, String transactionType,String custId,String accountNumber,String description);
}
