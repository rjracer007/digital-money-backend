package com.example.account_service.controller;

import com.example.account_service.dto.AccountResponseDTO;
import com.example.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // Se llama internamente cuando se registra un usuario
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestParam Long userId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint del Sprint 2 para obtener el balance y datos
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getAccountStatus(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(accountService.getAccountByUserId(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}