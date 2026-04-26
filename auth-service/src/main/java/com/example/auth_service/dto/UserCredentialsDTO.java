package com.example.auth_service.dto;

public record UserCredentialsDTO(
        Long id,
        String email,
        String password) {
}