package com.apushkin.service;

import com.apushkin.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BankTransactionsService {
    @Value(value = "${bank.slogan}")
    private String slogan;
    private List<Transaction> transactions = new ArrayList<>();

    public Transaction makeTransaction(int amount, String reference) {
        String id = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(id, amount, Instant.now(), reference, slogan);
        transactions.add(transaction);
        return transaction;
    }

    public List<Transaction> findAllTransactions() {
        return transactions;
    }
}
