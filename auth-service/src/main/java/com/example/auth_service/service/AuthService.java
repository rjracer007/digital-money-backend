package com.example.auth_service.service;

import com.example.auth_service.client.UserFeignClient;
import com.example.auth_service.dto.AuthRequestDTO;
import com.example.auth_service.dto.AuthResponseDTO;
import com.example.auth_service.dto.UserCredentialsDTO;
import com.example.auth_service.config.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserFeignClient userFeignClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthResponseDTO login(AuthRequestDTO request) {
        try {
            // 1. Buscamos el usuario en el user-service vía Feign
            UserCredentialsDTO user = userFeignClient.getUserByEmail(request.email());

            // 2. Verificamos la contraseña (el raw vs el encriptado de la BD)
            if (!passwordEncoder.matches(request.password(), user.password())) {
                throw new IllegalArgumentException("Contraseña incorrecta");
            }

            // 3. Generamos el token JWT
            String token = jwtProvider.generateToken(user.email());
            return new AuthResponseDTO(token);

        } catch (Exception e) {
            // Feign arroja un error si el user-service devuelve un 404
            throw new IllegalArgumentException("Credenciales inválidas o usuario no encontrado");
        }
    }
}