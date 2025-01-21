package com.apushkin.web;

import com.apushkin.model.ErrorObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {
//    TODO - Validation failed for argument [0] in public com.apushkin.model.Transaction com.apushkin.web.BankApiController.createTransaction(com.apushkin.model.Transaction) with 2 errors: [Field error in object 'transaction' on field 'reference': rejected value [null]; codes [NotBlank.transaction.reference,NotBlank.reference,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [transaction.reference,reference]; arguments []; default message [reference]]; default message [must not be blank]] [Field error in object 'transaction' on field 'amount': rejected value [500]; codes [Max.transaction.amount,Max.amount,Max.int,Max]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [transaction.amount,amount]; arguments []; default message [amount],100]; default message [must be less than or equal to 100]]
    @Test
    void parseError() {
        String test = """
                Validation failed for argument [0] in public com.apushkin.model.Transaction
                com.apushkin.web.BankApiController.createTransaction(com.apushkin.model.Transaction):
                [Field error in object 'transaction' on field 'reference': rejected value [null]; codes [NotBlank.transaction.reference,NotBlank.reference,NotBlank.java.lang.String,NotBlank];
                        arguments [
                            org.springframework.context.support.DefaultMessageSourceResolvable:
                                codes [transaction.reference,reference];
                                arguments [];
                                default message [reference]
                                ];
                        default message [must not be blank]
                ]
                """;
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        List<ErrorObject> errorObjects = globalExceptionHandler.parseError(test);
        assertNotNull(errorObjects);
        System.out.println(errorObjects);
    }
}