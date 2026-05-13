package com.example.account_service.service;

import com.example.account_service.dto.AccountResponseDTO;
import com.example.account_service.model.Account;
import com.example.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    // Palabras aleatorias para el alias
    private final String[] ALIAS_WORDS = { "sol", "luna", "mar", "rio", "montaña", "lobo", "aguila", "casa", "arbol",
            "fuego" };

    public AccountResponseDTO createAccount(Long userId) {
        // Verificamos que no tenga cuenta ya
        if (accountRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("El usuario ya tiene una cuenta asignada");
        }

        Account newAccount = Account.builder()
                .userId(userId)
                .cvu(generateCvu())
                .alias(generateAlias())
                .balance(BigDecimal.ZERO) // Saldo inicial $0.00
                .build();

        Account saved = accountRepository.save(newAccount);
        return mapToDTO(saved);
    }

    public AccountResponseDTO getAccountByUserId(Long userId) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada para este usuario"));
        return mapToDTO(account);
    }

    // --- Métodos Privados de Generación y Mapeo ---

    private String generateCvu() {
        Random random = new Random();
        StringBuilder cvu = new StringBuilder();
        for (int i = 0; i < 22; i++) {
            cvu.append(random.nextInt(10));
        }
        return cvu.toString();
    }

    private String generateAlias() {
        Random random = new Random();
        String w1 = ALIAS_WORDS[random.nextInt(ALIAS_WORDS.length)];
        String w2 = ALIAS_WORDS[random.nextInt(ALIAS_WORDS.length)];
        String w3 = ALIAS_WORDS[random.nextInt(ALIAS_WORDS.length)];
        return w1 + "." + w2 + "." + w3;
    }

    private AccountResponseDTO mapToDTO(Account account) {
        return new AccountResponseDTO(account.getUserId(), account.getCvu(), account.getAlias(), account.getBalance());
    }
}