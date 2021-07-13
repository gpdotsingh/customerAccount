package com.capgemini.customeraccount.configuration;

import com.capgemini.customeraccount.model.TransactionModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class TransactionLogging {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AppConfig appConfig;
    private  HttpHeaders headers;

    @Pointcut(value = "execution(* com.capgemini.customeraccount.dao.AccountTransactionDaoImpl.updateCurrentAccount(..) )")
    public void createAccount(){
        System.out.println("Transaction 1");

    }


    //@Pointcut(value = "execution(*com.capgemini.customeraccount.dao.AccountTransactionDaoImpl.creditDebitOperation(..))")
    @Around("createAccount()")
    public Object transactionLogging(ProceedingJoinPoint pjp){
        System.out.println("Transaction 2");


        String methodName = pjp.getSignature().getName();
        String className = pjp.getSignature().getName();
        Object[] array = pjp.getArgs();
        Object obj = null;


        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = appConfig.getTransactionUrl();
        Map<String, String> params = new HashMap<String, String>();
        params.put("transactioType", "CREDIT");

        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(params)
                .toUri();
        uri = UriComponentsBuilder
                .fromUri(uri)
                .queryParam("transactionTime", LocalDateTime.now())
                .queryParam("amount", 10)
                .queryParam("custId", "customer1")
                .queryParam("description", "Testing")
                .queryParam("transactioType", "CREDIT")
                .queryParam("transactionTime", LocalDateTime.now())
                .build()
                .toUri();
        HttpEntity<String> request = new HttpEntity(headers);
        restTemplate.exchange(uri , HttpMethod.POST,request, TransactionModel.class);



        try {
            obj = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return obj;
    }
}
