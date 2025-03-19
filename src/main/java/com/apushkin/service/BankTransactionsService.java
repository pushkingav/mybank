package com.apushkin.service;

import com.apushkin.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class BankTransactionsService {
    @Value(value = "${bank.slogan}")
    private String slogan;
    private final JdbcTemplate jdbcTemplate;

    public BankTransactionsService(@Value("${bank.slogan}") String slogan, JdbcTemplate jdbcTemplate) {
        this.slogan = slogan;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Transaction makeTransaction(int amount, String reference) {
        System.out.printf("Is database transaction open: %s%n", TransactionSynchronizationManager
                .isActualTransactionActive());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Instant transactionInstant = Instant.now();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into transactions (amount, time_stamp, reference) values (?, ?, ?)",
                            PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, amount);
            ps.setTimestamp(2, Timestamp.from(transactionInstant));
            ps.setString(3, reference);
            return ps;
        }, keyHolder);
        String uuid = !Objects.requireNonNull(keyHolder.getKeys().isEmpty())
                ? keyHolder.getKeys().values().iterator().next().toString()
                : null;
        Transaction transaction = new Transaction(uuid, amount, transactionInstant, reference, slogan);
        return transaction;
    }

    @Transactional
    public List<Transaction> findAllTransactions() {
        System.out.printf("Is database transaction open: %s%n", TransactionSynchronizationManager
                .isActualTransactionActive());

        return jdbcTemplate.query("select id, amount, time_stamp, reference from transactions",
                (resultSet, rowNum) -> {
                    Transaction transaction = new Transaction();
                    transaction.setId(resultSet.getObject("id").toString());
                    transaction.setAmount(resultSet.getInt("amount"));
                    transaction.setTimestamp(resultSet.getTimestamp("time_stamp").toInstant());
                    transaction.setReference(resultSet.getString("reference"));
                    transaction.setSlogan(slogan);
                    return transaction;
                });
    }
}
