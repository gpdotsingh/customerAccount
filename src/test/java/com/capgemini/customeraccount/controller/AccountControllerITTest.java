package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.controller.common.DataSetUp;
import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerITTest {


    @LocalServerPort
    private int port;

    @Autowired
    CustomerRepo customerRepo;

    private TestRestTemplate restTemplate;
    private static HttpHeaders headers;
    private StringBuilder customerControllerURL = new StringBuilder();
    @Mock
    private RestTemplate mockRestTemplate;

    @BeforeEach
    void setUp() throws IOException {
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        customerControllerURL.append("http://localhost:" + port + "/api/v1/accounts/");
        DataSetUp dataSetUp = new DataSetUp();
        dataSetUp.datasetUp(customerRepo);
    }

    @Test
    public void createAccountHappyFlow() {
        setURL("/customer4", "amount=33&transactionType=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.POST, request, String.class).getStatusCode(),HttpStatus.CREATED);
    }


    @Test
    public void createAccountNoCustomerFlow() {
        setURL("/customer490", "amount=33&transactionType=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.POST, request, String.class).getStatusCode(),HttpStatus.NOT_FOUND);
    }


    @Test
    public void updateCreateAccountHappyFlow() {
        setURL("/customer5", "amount=33&transactionType=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class).getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void insufficientAccountHappyFlow() {
        setURL("/customer5", "amount=33&transactionType=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class);
        setURL("/customer5", "amount=63&transactionType=DEBIT  ");
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class).getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    @Test
    public void debitAccountHappyFlow() {
        setURL("/customer5", "amount=33&transactionType=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class);
        setURL("/customer5", "amount=31&transactionType=DEBIT  ");
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class).getStatusCode(),HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateAccountHappyFlow() {
        setURL("/customer5", "amount=33&transactionType=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class).getStatusCode(),HttpStatus.CREATED);
    }

    @Test
    public void debitATCreateNotAllowed() {
        setURL("/customer5", "amount=33&transactionType=DEBIT");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.POST, request, String.class).getStatusCode(),HttpStatus.EXPECTATION_FAILED);
    }

    public void setURL(String s, String s2) {
        customerControllerURL.append(AccountEnum.CURRENT);
        customerControllerURL.append(s);
        customerControllerURL.append("?");
        customerControllerURL.append(s2);
    }

}
