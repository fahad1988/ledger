package com.teya.ledger.repository;

import com.teya.ledger.model.Transaction;
import com.teya.ledger.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private BigDecimal currentBalance = BigDecimal.ZERO;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Transaction save(Transaction transaction) {

        rwLock.writeLock().lock();
        try {
            transaction.setId(idGenerator.getAndIncrement());
            transactions.add(transaction);
            updateBalance(transaction);
            return transaction;
        }
        finally {
            rwLock.writeLock().unlock();
        }
    }

    public Page<Transaction> findAll(Pageable pageable) {
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), transactions.size());

        rwLock.readLock().lock();
        try {
            List<Transaction> pageContent = transactions.subList(startIndex, endIndex);
            return new PageImpl<>(pageContent, pageable, transactions.size());
        }
        finally {
            rwLock.readLock().unlock();
        }
    }

    /**
     * Update balances when a transaction is created - using atomic operations
     */
    private void updateBalance(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        boolean isDeposit = transaction.getTransactionType() == TransactionType.DEPOSIT;
        currentBalance = isDeposit ? currentBalance.add(amount) : currentBalance.subtract(amount);
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
}
