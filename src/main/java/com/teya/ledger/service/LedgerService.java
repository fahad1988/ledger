package com.teya.ledger.service;

import com.teya.ledger.dto.TransactionRequest;
import com.teya.ledger.model.Transaction;
import com.teya.ledger.model.TransactionType;
import com.teya.ledger.exception.InsufficientFundsException;
import com.teya.ledger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;

@Service
public class LedgerService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public LedgerService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(TransactionRequest request) {

        if(request.getTransactionType() == TransactionType.WITHDRAWAL) {
            BigDecimal currentBalance = getCurrentBalance();
            if (currentBalance.compareTo(request.getAmount()) < 0) {
                throw new InsufficientFundsException("Insufficient funds for withdrawal");
            }
        }
        Transaction transaction = new Transaction(null, request.getAmount(), request.getDescription(), request.getTransactionType());
        return transactionRepository.save(transaction);
    }

       public BigDecimal getCurrentBalance() {
        return transactionRepository.getCurrentBalance();
    }

    public Page<Transaction> getPaginatedTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

}