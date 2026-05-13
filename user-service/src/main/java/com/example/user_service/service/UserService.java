package com.example.user_service.service;

import com.example.user_service.client.AccountFeignClient;
import com.example.user_service.dto.UserRegisterDTO;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountFeignClient accountFeignClient;

    @Transactional
    public User registerUser(UserRegisterDTO dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        User newUser = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .phone(dto.phone())
                .build();

        User savedUser = userRepository.save(newUser);

        accountFeignClient.createAccount(savedUser.getId());

        return savedUser;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
}