package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.enums.AccountEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerITTest {


    @LocalServerPort
    private int port;

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

    }


    @Test
    public void createAccountHappyFlow() {
        customerControllerURL.append(AccountEnum.CURRENT);
        customerControllerURL.append("/customer4");
        customerControllerURL.append("?");
        customerControllerURL.append("amount=33&transactionTypeEnum=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.POST, request, String.class).getStatusCode(),HttpStatus.CREATED);
    }


    @Test
    public void createAccountNoCustomerFlow() {
        customerControllerURL.append(AccountEnum.CURRENT);
        customerControllerURL.append("/customer490");
        customerControllerURL.append("?");
        customerControllerURL.append("amount=33&transactionTypeEnum=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.POST, request, String.class).getStatusCode(),HttpStatus.PAYMENT_REQUIRED);
    }


    @Test
    public void updateCreateAccountHappyFlow() {
        customerControllerURL.append(AccountEnum.CURRENT);
        customerControllerURL.append("/customer5");
        customerControllerURL.append("?");
        customerControllerURL.append("amount=33&transactionTypeEnum=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class).getStatusCode(),HttpStatus.CREATED);
    }

    @Test
    public void updateAccountHappyFlow() {
        customerControllerURL.append(AccountEnum.CURRENT);
        customerControllerURL.append("/customer5");
        customerControllerURL.append("?");
        customerControllerURL.append("amount=33&transactionTypeEnum=CREDIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class).getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void debitATCreateNotAllowed() {
        customerControllerURL.append(AccountEnum.CURRENT);
        customerControllerURL.append("/customer5");
        customerControllerURL.append("?");
        customerControllerURL.append("amount=33&transactionTypeEnum=DEBIT");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.POST, request, String.class).getStatusCode(),HttpStatus.EXPECTATION_FAILED);
    }

    @Test
    public void debitInsufficientAmount() {
        customerControllerURL.append(AccountEnum.CURRENT);
        customerControllerURL.append("/customer5");
        customerControllerURL.append("?");
        customerControllerURL.append("amount=3354&transactionTypeEnum=DEBIT  ");
        HttpEntity<String> request = new HttpEntity<>( headers);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString(), HttpMethod.PATCH, request, String.class).getStatusCode(),HttpStatus.EXPECTATION_FAILED);
    }
}
