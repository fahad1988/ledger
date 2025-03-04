package com.teya.ledger.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private Long id;
    private LocalDateTime timestamp;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;

    public Transaction(Long id, BigDecimal amount, String description, TransactionType transactionType) {
        this.id = id;
        this.timestamp = LocalDateTime.now();
        this.amount = amount;
        this.description = description;
        this.transactionType = transactionType;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}

