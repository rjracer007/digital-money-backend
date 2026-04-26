package com.example.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String firstName,
        
        @NotBlank(message = "El apellido es obligatorio")
        String lastName,
        
        @Email(message = "Debe ser un email válido")
        @NotBlank(message = "El email es obligatorio")
        String email,
        
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,
        
        String phone
) {}