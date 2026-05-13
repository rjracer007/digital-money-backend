package com.example.account_service.controller;

import com.example.account_service.dto.*;
import com.example.account_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/{accountId}/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long accountId, @RequestBody DepositRequestDTO request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.makeDeposit(accountId, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@PathVariable Long accountId, @RequestBody TransferRequestDTO request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.makeTransfer(accountId, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getHistory(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getHistory(accountId));
    }
}