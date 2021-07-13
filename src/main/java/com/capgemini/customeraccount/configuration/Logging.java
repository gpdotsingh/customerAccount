package com.capgemini.customeraccount.configuration;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;

@Aspect
public class Logging {

    /**
     * This is the method which I would like to execute
     * after a selected method execution throws exception.
     */
    @AfterThrowing(PointCut = "execution(* com.tutorialspoint.Student.*(..))",
            throwing = "error")
    public void afterThrowingAdvice(JoinPoint jp, Throwable error){
        System.out.println("Method Signature: "  + jp.getSignature());
        System.out.println("Exception: "+error);
    }
}
