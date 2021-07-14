package com.capgemini.customeraccount.exception;

import com.capgemini.customeraccount.enums.ExceptionMessageEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {


    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> customerNotFound(
            CustomerNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ExceptionMessageEnum.EXCEPTION_TIME.name(), LocalDateTime.now());
        body.put(ExceptionMessageEnum.CUSTOMER_NOT_FOUND.name(), ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<Object> invalidAccount(
            AccountException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ExceptionMessageEnum.EXCEPTION_TIME.name(), LocalDateTime.now());
        body.put(ExceptionMessageEnum.INVALID_ACCOUNT_TYPE.name(), ex.getLocalizedMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Object> genericExceptions(GenericException ex,WebRequest request)
    {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ExceptionMessageEnum.EXCEPTION_TIME.name(), LocalDateTime.now());
        body.put(ExceptionMessageEnum.ERROR.name(), ex.getLocalizedMessage());
        return new ResponseEntity<>(body,HttpStatus.PAYMENT_REQUIRED);
    }

}