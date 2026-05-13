package com.example.account_service.dto;

public record CardResponseDTO(
        Long id,
        String maskedCardNumber,
        String nameOnCard,
        String expirationDate) {
}