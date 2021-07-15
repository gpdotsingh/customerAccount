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

    @Autowired
    TransactionDao transactionDao;

    @AfterThrowing(pointcut = "execution(* com.capgemini.customeraccount.dao..updateCurrentAccount(..) )",
            throwing = "error")
    public void afterFailTransaction(JoinPoint joinPoint, Throwable error){
        initTransactionLogging(joinPoint);

    }

    @AfterReturning("execution(* com.capgemini.customeraccount.dao.CurrentAccountDaoImpl.updateCurrentAccount(..) )")
    public void logBeforeUpdateAccount(JoinPoint joinPoint) {
        initTransactionLogging(joinPoint);
    }

    @AfterReturning(pointcut="execution(* com.capgemini.customeraccount.dao.CurrentAccountDaoImpl.createCurrentAccount(..) )",returning = "accountEntitySaved")
    public void  logAfterCreateAccount( AccountEntity accountEntitySaved) {

        transactionDao.logTransaction(
                String.valueOf(
                        accountEntitySaved.getCurrentAccountEntity().getAmount())
                , "CREDIT"
                , accountEntitySaved.getCurrentAccountEntity().getCustomerId()
                , accountEntitySaved.getCurrentAccountEntity().getAccountNumber()
                , "Current account transaction");
    }

    private void initTransactionLogging(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();

        String amount = String.valueOf(signatureArgs[0]) ;
        String transactionType = String.valueOf(signatureArgs[1]);
        String custId = String.valueOf(signatureArgs[2]);
        String accountNumber = String.valueOf(signatureArgs[3]);
        transactionDao.logTransaction(amount, transactionType, custId, accountNumber, "Current account transaction");
    }

}
