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

/***
 * This class will provide feature to call transaction services by using spring AOP feature
 *
 * Will call transaction services on creation of account, update of account also send transaction
 * when any exception occur during account operation
 */
@Aspect
@Component
@Slf4j
public class TransactionLogging {

    @Autowired
    TransactionDao transactionDao;

    /**
     * Call transaction to register failed transaction
     * @param joinPoint
     * @param error
     */
    @AfterThrowing(pointcut = "execution(* com.capgemini.customeraccount.dao.CurrentAccountDaoImpl.updateCurrentAccount(..) )",
            throwing = "error")
    public void afterFailTransaction(JoinPoint joinPoint, Throwable error){
        initTransactionLogging(joinPoint);
    }

    /**
     * Call transaction services on account update
     * @param joinPoint
     */
    @AfterReturning("execution(* com.capgemini.customeraccount.dao.CurrentAccountDaoImpl.updateCurrentAccount(..) )")
    public void logBeforeUpdateAccount(JoinPoint joinPoint) {
        initTransactionLogging(joinPoint);
    }

    /**
     * Call transaction services after account creation
     * @param accountEntitySaved
     */
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

    /**
     *Call transaction services
     * @param joinPoint
     */
    private void initTransactionLogging(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();

        String amount = String.valueOf(signatureArgs[0]) ;
        String transactionType = String.valueOf(signatureArgs[1]);
        String custId = String.valueOf(signatureArgs[2]);
        String accountNumber = String.valueOf(signatureArgs[3]);
        transactionDao.logTransaction(amount, transactionType, custId, accountNumber, "Current account transaction");
    }

}
