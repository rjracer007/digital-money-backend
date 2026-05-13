package com.example.account_service.controller;

import com.example.account_service.dto.CardRequestDTO;
import com.example.account_service.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/{accountId}/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<?> addCard(@PathVariable Long accountId, @Valid @RequestBody CardRequestDTO request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cardService.saveCard(accountId, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCards(@PathVariable Long accountId) {
        try {
            return ResponseEntity.ok(cardService.getCardsByAccountId(accountId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}