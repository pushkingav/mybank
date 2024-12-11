package com.apushkin.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

public class Transaction {
    private String id;
    private int amount;
    @JsonFormat(pattern ="yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Instant timestamp;
    private String reference;
    private String slogan;

    public Transaction(String id, int amount, Instant timestamp, String reference, String slogan) {
        this.id = id;
        this.amount = amount;
        this.timestamp = timestamp;
        this.reference = reference;
        this.slogan = slogan;
    }

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Transaction.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("amount=" + amount)
                .add("timestamp=" + timestamp)
                .add("reference='" + reference + "'")
                .toString();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;

        return amount == that.amount
                && Objects.equals(id, that.id)
                && Objects.equals(timestamp, that.timestamp)
                && Objects.equals(reference, that.reference);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + amount;
        result = 31 * result + Objects.hashCode(timestamp);
        result = 31 * result + Objects.hashCode(reference);
        return result;
    }
}
