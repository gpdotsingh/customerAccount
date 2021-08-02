package com.capgemini.customeraccount.controller;

import com.capgemini.customeraccount.configuration.TransactionLogging;
import com.capgemini.customeraccount.dao.CurrentAccountDao;
import com.capgemini.customeraccount.dao.CurrentAccountDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransactionLoggingITTest {

@Test
    public void aopTest(){
    CurrentAccountDao target = new CurrentAccountDaoImpl();
    AspectJProxyFactory factory = new AspectJProxyFactory(target);
    TransactionLogging aspect = new TransactionLogging();
    factory.addAspect(aspect);
    CurrentAccountDao proxy = factory.getProxy();
    System.out.println(proxy);
}
}
