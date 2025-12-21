package org.example.creditcard.service;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.entity.Transaction;
import org.example.creditcard.model.enums.TransactionStatus;
import org.example.creditcard.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardService cardService;
    private final BillingService billingService;

    public TransactionService(
            TransactionRepository transactionRepository,
            CardService cardService,
            BillingService billingService) {

        this.transactionRepository = transactionRepository;
        this.cardService = cardService;
        this.billingService = billingService;
    }

    public Transaction makeTransaction(
            Card card,
            String merchantName,
            BigDecimal amount) {

        if (card.getAvailableBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Deduct balance
        card.setAvailableBalance(card.getAvailableBalance().subtract(amount));
        cardService.updateCard(card);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setMerchantName(merchantName);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.SUCCESS);

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Add to bill
        Bill bill = billingService.getOrCreateActiveBill(card);
        bill.setTotalAmount(bill.getTotalAmount().add(amount));
        billingService.updateBill(bill);

        return savedTransaction;
    }
}
