package com.example.account_service.dto;

import java.math.BigDecimal;

public record AccountResponseDTO(Long userId, String cvu, String alias, BigDecimal balance) {
}