package com.capgemini.customeraccount.configuration;

import com.capgemini.customeraccount.dao.TransactionDao;
import com.capgemini.customeraccount.entity.AccountEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TransactionLogging {

    private Logger logger = LoggerFactory.getLogger(TransactionLogging.class);
    private String amount; private String transactionType;private String custId ;private String accountNumber ; private String description;

    @Autowired
    TransactionDao transactionDao;

    @AfterThrowing(pointcut = "execution(* com.capgemini.customeraccount.dao..updateCurrentAccount(..) )",
            throwing = "error")
    public void afterFailTransaction(JoinPoint joinPoint, Throwable error){
        initTransactionLogging(joinPoint);

    }

    @Before("execution(* com.capgemini.customeraccount.dao.CurrentAccountDaoImpl.updateCurrentAccount(..) )")
    public void logBeforeUpdateAccount(JoinPoint joinPoint) { System.out.println("---"+joinPoint.getSignature());
        initTransactionLogging(joinPoint);
    }

    @AfterReturning(pointcut="execution(* com.capgemini.customeraccount.dao.CurrentAccountDaoImpl.createCurrentAccount(..) )",returning = "accountEntitySaved")
    public void  logBeforeCreateAccount( AccountEntity accountEntitySaved) {

        logger.error(accountEntitySaved.getAccountEnum()+"  "+  accountEntitySaved.getCurrentAccountEntity().getAccountNumber()+"  "+        accountEntitySaved.getCurrentAccountEntity().getCustomerId());

    }
    private void initTransactionLogging(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();

        amount = String.valueOf(signatureArgs[0]) ;
        transactionType = String.valueOf(signatureArgs[1]);
        custId = String.valueOf(signatureArgs[2]);
        accountNumber = String.valueOf(signatureArgs[3]);
        transactionDao.logTransaction(amount, transactionType, custId, accountNumber, "Current account transaction");
    }







}
