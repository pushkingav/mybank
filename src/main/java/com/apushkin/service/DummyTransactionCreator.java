package com.apushkin.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DummyTransactionCreator {
    private final BankTransactionsService bankTransactionsService;

    public DummyTransactionCreator(BankTransactionsService bankTransactionsService) {
        this.bankTransactionsService = bankTransactionsService;
    }

    @PostConstruct
    public void init() {
        System.out.println("Generating some transactions");
        bankTransactionsService.makeTransaction(5, "Dummy transaction #1");
        bankTransactionsService.makeTransaction(15, "Dummy transaction #2");
        bankTransactionsService.makeTransaction(25, "Give some money to grandma");
    }
}
