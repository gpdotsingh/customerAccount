package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.controller.common.DataSetUp;
import com.capgemini.customeraccount.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerITTest {

    @LocalServerPort
    private int port;
    @Autowired
    CustomerRepo customerRepo;
    private TestRestTemplate restTemplate;
    private static HttpHeaders headers;
    private StringBuilder customerControllerURL = new StringBuilder();
    @BeforeEach
    void setUp() throws IOException {
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        customerControllerURL.append("http://localhost:" + port + "/api/v1/customers/");
        DataSetUp dataSetUp = new DataSetUp();
        dataSetUp.datasetUp(customerRepo);
    }

    @Test
    public void getCustomerPageHappyFlow()
    {
        HttpEntity<String> request = new HttpEntity<>(headers);
        assertTrue(this.restTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.GET,new HttpEntity<>(headers),String.class).getStatusCode()== HttpStatus.OK);

    }

    @Test
    public void getCustomerInfoHappyFlow()
    {
        customerControllerURL.append("/customer1");
        String customers = this.restTemplate.getForObject(customerControllerURL.toString(), String.class);
        assertTrue(this.restTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.GET,new HttpEntity<>(headers),String.class).getStatusCode()== HttpStatus.OK);
    }

    @Test
    public void getCustomerInfoUnHappyFlow()
    {
        customerControllerURL.append("/customer91");
        String customers = this.restTemplate.getForObject(customerControllerURL.toString(), String.class);
        assertEquals(this.restTemplate.exchange(customerControllerURL.toString()
                , HttpMethod.GET,new HttpEntity<>(headers),String.class).getStatusCode(), HttpStatus.NOT_FOUND);
    }
}