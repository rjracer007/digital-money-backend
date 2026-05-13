package com.example.account_service.service;

import com.example.account_service.dto.*;
import com.example.account_service.model.*;
import com.example.account_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionResponseDTO makeDeposit(Long accountId, DepositRequestDTO request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a cero");
        }

        account.setBalance(account.getBalance().add(request.amount()));
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .accountId(account.getId())
                .amount(request.amount())
                .type("DEPOSIT")
                .description(request.description())
                .timestamp(LocalDateTime.now())
                .build();

        return mapToResponse(transactionRepository.save(transaction));
    }

    @Transactional
    public TransactionResponseDTO makeTransfer(Long originAccountId, TransferRequestDTO request) {
        Account originAccount = accountRepository.findById(originAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de origen no encontrada"));

        Account destAccount = accountRepository.findByCvu(request.destinationCvu())
                .orElseThrow(() -> new IllegalArgumentException("El CVU de destino no existe"));

        if (originAccount.getBalance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la transferencia");
        }

        originAccount.setBalance(originAccount.getBalance().subtract(request.amount()));
        destAccount.setBalance(destAccount.getBalance().add(request.amount()));

        accountRepository.save(originAccount);
        accountRepository.save(destAccount);

        Transaction txOrigin = Transaction.builder()
                .accountId(originAccount.getId())
                .amount(request.amount().negate())
                .type("TRANSFER_OUT")
                .description(request.description())
                .timestamp(LocalDateTime.now())
                .build();
        transactionRepository.save(txOrigin);

        Transaction txDest = Transaction.builder()
                .accountId(destAccount.getId())
                .amount(request.amount())
                .type("TRANSFER_IN")
                .description("Transferencia recibida de: " + originAccount.getAlias())
                .timestamp(LocalDateTime.now())
                .build();
        transactionRepository.save(txDest);

        return mapToResponse(txOrigin);
    }

    public List<TransactionResponseDTO> getHistory(Long accountId) {
        return transactionRepository.findByAccountIdOrderByTimestampDesc(accountId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponseDTO mapToResponse(Transaction tx) {
        return new TransactionResponseDTO(tx.getId(), tx.getAmount(), tx.getType(), tx.getDescription(),
                tx.getTimestamp());
    }
}