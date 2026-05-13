package com.example.account_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencia al usuario del user-service
    @Column(nullable = false, unique = true)
    private Long userId;

    // El CVU autogenerado de 22 dígitos
    @Column(nullable = false, unique = true, length = 22)
    private String cvu;

    // El Alias autogenerado (ej. casa.perro.sol)
    @Column(nullable = false, unique = true)
    private String alias;

    // Usamos BigDecimal para manejar dinero con precisión exacta
    @Column(nullable = false)
    private BigDecimal balance;
}