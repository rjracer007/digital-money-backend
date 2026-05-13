package com.example.account_service.service;

import com.example.account_service.dto.CardRequestDTO;
import com.example.account_service.dto.CardResponseDTO;
import com.example.account_service.model.Account;
import com.example.account_service.model.Card;
import com.example.account_service.repository.AccountRepository;
import com.example.account_service.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public CardResponseDTO saveCard(Long accountId, CardRequestDTO request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("La cuenta no existe"));

        Card newCard = Card.builder()
                .accountId(account.getId())
                .cardNumber(request.cardNumber())
                .nameOnCard(request.nameOnCard())
                .expirationDate(request.expirationDate())
                .cvv(request.cvv())
                .build();

        Card savedCard = cardRepository.save(newCard);
        return mapToResponse(savedCard);
    }

    public List<CardResponseDTO> getCardsByAccountId(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new IllegalArgumentException("La cuenta no existe");
        }

        return cardRepository.findByAccountId(accountId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CardResponseDTO mapToResponse(Card card) {
        String maskedNumber = "**** **** **** " + card.getCardNumber().substring(card.getCardNumber().length() - 4);
        return new CardResponseDTO(card.getId(), maskedNumber, card.getNameOnCard(), card.getExpirationDate());
    }
}