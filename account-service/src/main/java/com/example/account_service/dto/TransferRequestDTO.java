package com.example.account_service.dto;

import java.math.BigDecimal;

public record TransferRequestDTO(BigDecimal amount, String destinationCvu, String description) {
}