package org.example.creditcard.service;

import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.entity.Transaction;
import org.example.creditcard.model.enums.CardStatus;
import org.example.creditcard.model.enums.TransactionStatus;
import org.example.creditcard.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardService cardService;

    public TransactionService(
            TransactionRepository transactionRepository,
            CardService cardService) {

        this.transactionRepository = transactionRepository;
        this.cardService = cardService;
    }

    public Transaction makeTransaction(
            Card card,
            String merchantName,
            BigDecimal amount) {

        // 🔒 Block transactions if card is blocked
        if (card.getStatus() == CardStatus.BLOCKED) {
            throw new RuntimeException(
                    "Card is blocked. Pay your bill to continue transactions."
            );
        }

        // 💰 Check credit
        if (card.getAvailableBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient credit");
        }

        // 💳 Deduct balance
        card.setAvailableBalance(
                card.getAvailableBalance().subtract(amount)
        );
        cardService.updateCard(card);

        // 🧾 Create transaction (UNBILLED)
        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setMerchantName(merchantName);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setBilled(false);   // ⭐ VERY IMPORTANT

        return transactionRepository.save(transaction);
    }
}
