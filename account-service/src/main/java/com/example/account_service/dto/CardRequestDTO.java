package com.example.account_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CardRequestDTO(
        @NotBlank String cardNumber,
        @NotBlank String nameOnCard,
        @NotBlank String expirationDate,
        @NotBlank @Size(min = 3, max = 3) String cvv) {
}