package com.capgemini.customeraccount.dao;

import com.capgemini.customeraccount.configuration.AppConfig;
import com.capgemini.customeraccount.enums.ExceptionMessageEnum;
import com.capgemini.customeraccount.exception.GenericException;
import com.capgemini.customeraccount.model.TransactionModel;
import com.capgemini.customeraccount.model.TransactionPageModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.ConnectException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TransactionDaoImpl implements TransactionDao {

    private Logger logger = LoggerFactory.getLogger(TransactionDaoImpl.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AppConfig appConfig;


    TransactionPageModel transactionModels;
    ResponseEntity<TransactionPageModel> transactionPage;

    /**
     * Check if account number exists in table, else throw exception
     * call transaction service
     * if status code ok transform it and send it else to do
     * @param pageSize
     * @param pageNo
     * @param accountNumber
     * @return
     */
    @Override
    public TransactionPageModel getTransaction(int pageSize, int pageNo, String accountNumber) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(appConfig.getTransactionUrl());
        uriBuilder.queryParam("pageNo", pageNo);
        uriBuilder.queryParam("pageSize", pageSize);
        uriBuilder.queryParam("accountNumber", accountNumber);
    try {
        transactionPage = restTemplate.getForEntity(uriBuilder.build().toString(),
                TransactionPageModel.class);
        if (transactionPage.getStatusCode() != HttpStatus.OK) {
            throw new GenericException(ExceptionMessageEnum.TRY_AFTER_SOMETIME2.name());
        }
        transactionModels = transactionPage.getBody();
    }
    catch (Exception e)
    {
        logger.error(e.getMessage());
        throw new GenericException(ExceptionMessageEnum.TRY_AFTER_SOMETIME2.name());

    }
        return transactionModels;

    }

    /**
     * Log the transactions in transaction service
     * @param amount
     * @param transactionType
     * @param custId
     * @param accountNumber
     * @param description
     */
    @Override
    public void logTransaction(String amount, String transactionType,String custId,String accountNumber,String description) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String url = appConfig.getTransactioTypeURL();
            Map<String, String> params = new HashMap();
            params.put("transactioType", transactionType);

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(params)
                    .toUri();
            uri = UriComponentsBuilder
                    .fromUri(uri)
                    .queryParam("amount", amount)
                    .queryParam("custId", custId)
                    .queryParam("description", description)
                    .queryParam("transactioType", transactionType)
                    .queryParam("transactionTime", LocalDateTime.now())
                    .queryParam("accountNumber", accountNumber)
                    .build()
                    .toUri();

            HttpEntity<String> request = new HttpEntity(headers);
            restTemplate.exchange(uri, HttpMethod.POST, request, TransactionModel.class);
        }
        catch (Exception e)
        {
            logger.error("Do something",e.getMessage());
        }
    }

}
