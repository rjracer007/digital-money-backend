package com.example.account_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(Long id, BigDecimal amount, String type, String description,
        LocalDateTime timestamp) {
}