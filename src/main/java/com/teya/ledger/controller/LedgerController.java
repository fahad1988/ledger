package com.teya.ledger.controller;

import com.teya.ledger.dto.BalanceResponse;
import com.teya.ledger.dto.TransactionRequest;
import com.teya.ledger.model.Transaction;
import com.teya.ledger.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/api/v1/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    @Autowired
    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionRequest request) {

        Transaction transaction = ledgerService.createTransaction(request);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        BalanceResponse balance = new BalanceResponse(ledgerService.getCurrentBalance());
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Page<Transaction>> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions = ledgerService.getPaginatedTransactions(pageable);
        return ResponseEntity.ok(transactions);
    }

}
