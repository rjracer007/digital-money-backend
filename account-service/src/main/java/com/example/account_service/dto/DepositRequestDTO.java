package com.example.account_service.dto;

import java.math.BigDecimal;

public record DepositRequestDTO(BigDecimal amount, String description) {
}