package org.example.creditcard.service;

import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.enums.CardStatus;
import org.example.creditcard.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    /**
     * STEP 1: Customer requests a card
     * Card is NOT usable yet
     */
    public Card requestCard(Card card) {
        card.setStatus(CardStatus.PENDING);
        card.setAvailableBalance(BigDecimal.ZERO);
        return cardRepository.save(card);
    }

    /**
     * STEP 2: Banker/Admin approves card
     * Card becomes ACTIVE and usable
     */
    public Card approveCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setStatus(CardStatus.ACTIVE);
        card.setAvailableBalance(card.getCreditLimit());

        return cardRepository.save(card);
    }

    /**
     * Fetch a card by ID
     */
    public Optional<Card> getCardById(Long cardId) {
        return cardRepository.findById(cardId);
    }

    /**
     * Fetch all pending card requests (for Admin/Banker)
     */
    public List<Card> getPendingCards() {
        return cardRepository.findByStatus(CardStatus.PENDING);
    }

    /**
     * Generic update (used internally if needed)
     */
    public Card updateCard(Card card) {
        return cardRepository.save(card);
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

}
