package com.apushkin.service;

import com.apushkin.model.Transaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankTransactionsService {
    private List<Transaction> transactions = new ArrayList<>();

    public Transaction makeTransaction(int amount, String reference) {
        String id = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(id, amount, Instant.now(), reference);
        transactions.add(transaction);
        return transaction;
    }

    public List<Transaction> findAllTransactions() {
        return transactions;
    }
}
