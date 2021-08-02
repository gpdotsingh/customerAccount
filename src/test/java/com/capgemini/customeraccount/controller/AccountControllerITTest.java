package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.configuration.AppConfig;
import com.capgemini.customeraccount.configuration.TransactionLogging;
import com.capgemini.customeraccount.controller.common.DataSetUp;
import com.capgemini.customeraccount.dao.TransactionDao;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.model.TransactionPageModel;
import com.capgemini.customeraccount.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerITTest {


    @LocalServerPort
    private int port;

    @Autowired
    CustomerRepo customerRepo;
    @MockBean
    TransactionDao transactionDao;
    @MockBean
    RestTemplate mrestTemplate;
    @Autowired
    AppConfig appConfig;

    private TestRestTemplate restTemplate;
    private static HttpHeaders headers;
    private StringBuilder customerControllerURL = new StringBuilder();

    @BeforeEach
    void setUp() throws IOException {

        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        DataSetUp dataSetUp = new DataSetUp();
        dataSetUp.datasetUp(customerRepo);
    }

    @Test
    public void createAccountHappyFlow() {
        setURL("/customer4", "amount=33&transactionType=CREDIT  ",AccountEnum.CURRENT.toString());


        ResponseEntity mocResponse = new ResponseEntity("Some json string", HttpStatus.OK);

        Mockito.when(mrestTemplate.getForEntity(appConfig.getTransactioTypeURL().toString(),TransactionPageModel.class))
                .thenReturn(mocResponse );

        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.PUT, request, String.class).getStatusCode(),HttpStatus.CREATED);
        Mockito.verify(transactionDao).logTransaction(any(),any(),anyString(),anyString(),anyString());
    }

  @Test
    public void updateCreateAccountHappyFlow() {

        setURL("/customer5", "amount=33&transactionType=CREDIT  ",AccountEnum.CURRENT.toString());
        HttpEntity<String> request = new HttpEntity<>( headers);
        this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PUT, request, String.class);

        assertEquals(this.restTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.PUT, request, String.class).getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void insufficientAccountHappyFlow() {

        setURL("/customer5", "amount=33&transactionType=CREDIT  ",AccountEnum.CURRENT.toString());
        HttpEntity<String> request = new HttpEntity<>( headers);
        this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PUT, request, String.class);
        setURL("/customer5", "amount=63&transactionType=DEBIT  ",AccountEnum.CURRENT.toString());

        assertEquals(this.restTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.PUT, request, String.class).getStatusCode(),HttpStatus.PAYMENT_REQUIRED);
    }

    @Test
    public void debitAccountHappyFlow() {

        setURL("/customer5", "amount=3&transactionType=CREDIT  ",AccountEnum.CURRENT.toString());
        HttpEntity<String> request = new HttpEntity<>( headers);
        this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PUT, request, String.class);
        setURL("/customer5", "amount=31&transactionType=DEBIT  ",AccountEnum.CURRENT.toString());

        assertEquals(this.restTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.PUT, request, String.class).getStatusCode(),HttpStatus.PAYMENT_REQUIRED);
    }

    @Test
    public void updateAccountHappyFlow() {

        setURL("/customer5", "amount=33&transactionType=CREDIT  ",AccountEnum.CURRENT.toString());
        HttpEntity<String> request = new HttpEntity<>( headers);

        assertEquals(this.restTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.PUT, request, String.class).getStatusCode(),HttpStatus.CREATED);
    }

    @Test
    public void debitATCreateNotAllowed() {

        setURL("/customer5", "amount=33&transactionType=DEBIT",AccountEnum.CURRENT.toString());
        HttpEntity<String> request = new HttpEntity<>( headers);

        assertEquals(this.restTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.PUT, request, String.class).getStatusCode(),HttpStatus.EXPECTATION_FAILED);
    }

    public void setURL(String s, String s2,String accountType) {
        customerControllerURL = new StringBuilder();
        customerControllerURL.append("http://localhost:" + port + "/api/v1/accounts/");
        customerControllerURL.append(accountType);
        customerControllerURL.append(s);
        customerControllerURL.append("?");
        customerControllerURL.append(s2);
    }

}
