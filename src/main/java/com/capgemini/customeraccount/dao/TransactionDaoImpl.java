package com.capgemini.customeraccount.dao;

import com.capgemini.customeraccount.configuration.AppConfig;
import com.capgemini.customeraccount.model.TransactionPageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TransactionDaoImpl implements TransactionDao{

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AppConfig appConfig;
    TransactionPageModel transactionModels ;

    // Check if account number exists in table, else throw exception
    // call transaction service
    // if status code ok transform it and send it else to do

    @Override
    public TransactionPageModel getTransaction(int pageSize, int pageNo, String accountNumber) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(appConfig.getTransactionUrl());
        uriBuilder.queryParam("pageNo",pageNo);
        uriBuilder.queryParam("pageSize",pageSize);
        uriBuilder.queryParam("accountNumber",accountNumber);

        ResponseEntity<TransactionPageModel> transactionPage = restTemplate.getForEntity(uriBuilder.build().toString(),
                TransactionPageModel.class);
        if(transactionPage.getStatusCode()!= HttpStatus.OK)
        {
            // to do
        }
        transactionModels = transactionPage.getBody();

        return null;

    }
}
