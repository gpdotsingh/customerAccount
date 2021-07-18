package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.configuration.AppConfig;
import com.capgemini.customeraccount.dao.TransactionDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransactionControllerITTest {

    @LocalServerPort
    private int port;
    @InjectMocks
    TransactionDaoImpl transactionDao;
    @Mock
    AppConfig appConfig;
    @Mock
    private RestTemplate mockRestTemplate;

    private StringBuilder url = new StringBuilder();
    private TestRestTemplate testRestTemplate;
    private static HttpHeaders headers;
    private StringBuilder customerControllerURL = new StringBuilder();

    @BeforeEach
    void setUp() throws IOException {
        testRestTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        customerControllerURL.append("http://localhost:" + port + "/api/v1/transaction/accountnumber");
        url.append(appConfig.getTransactionUrl());
        url.append("/accountnumber");
    }

    @Test
    public void getTransactions(){
        ResponseEntity mocResponse = new ResponseEntity("Some json string", HttpStatus.OK);

       when(mockRestTemplate.getForEntity(anyString(), any()))
               .thenReturn(mocResponse);

        assertEquals(this.testRestTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.GET,new HttpEntity<>(headers),String.class).getStatusCode()
                ,HttpStatus.NOT_FOUND);;


    }
}
