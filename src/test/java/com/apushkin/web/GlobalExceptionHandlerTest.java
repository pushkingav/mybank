package com.apushkin.web;

import com.apushkin.model.ErrorObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {
    @Test
    void parseOneFieldErrorTest() {
        String test = "Validation failed for argument [0] in public com.apushkin.model.Transaction com.apushkin.web.BankApiController.createTransaction(com.apushkin.model.Transaction): [Field error in object 'transaction' on field 'reference': rejected value [null]; codes [NotBlank.transaction.reference,NotBlank.reference,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [transaction.reference,reference]; arguments []; default message [reference]]; default message [must not be blank]]";
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        List<ErrorObject> errorObjects = globalExceptionHandler.parseError(test);
        assertNotNull(errorObjects);
        assertEquals(1, errorObjects.size());

        ErrorObject errorObject = errorObjects.get(0);
        assertNotNull(errorObject);
        assertEquals(errorObject.getFieldName(), "reference");
        assertEquals(errorObject.getRejectedValue(), "null");
    }

    @Test
    void parseTwoFieldsErrorTest() {
        String test = "Validation failed for argument [0] in public com.apushkin.model.Transaction com.apushkin.web.BankApiController.createTransaction(com.apushkin.model.Transaction) with 2 errors: [Field error in object 'transaction' on field 'reference': rejected value [null]; codes [NotBlank.transaction.reference,NotBlank.reference,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [transaction.reference,reference]; arguments []; default message [reference]]; default message [must not be blank]] [Field error in object 'transaction' on field 'amount': rejected value [500]; codes [Max.transaction.amount,Max.amount,Max.int,Max]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [transaction.amount,amount]; arguments []; default message [amount],100]; default message [must be less than or equal to 100]]";
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        List<ErrorObject> errorObjects = globalExceptionHandler.parseError(test);

        assertNotNull(errorObjects);
        assertEquals(errorObjects.size(), 2);

        ErrorObject errorObject01 = errorObjects.get(0);
        assertNotNull(errorObject01);
        assertEquals(errorObject01.getFieldName(), "reference");
        assertEquals(errorObject01.getRejectedValue(), "null");

        ErrorObject errorObject02 = errorObjects.get(1);
        assertNotNull(errorObject02);
        assertEquals(errorObject02.getFieldName(), "amount");
        assertEquals(errorObject02.getRejectedValue(), "500");
    }
}