package org.example.creditcard.service;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.entity.Transaction;
import org.example.creditcard.model.entity.User;
import org.example.creditcard.model.enums.TransactionStatus;
import org.example.creditcard.repository.TransactionRepository;
import org.example.creditcard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardService cardService;
    private final BillingService billingService;
    private final UserRepository userRepository;

    public TransactionService(
            TransactionRepository transactionRepository,
            CardService cardService,
            BillingService billingService, UserRepository userRepository) {

        this.transactionRepository = transactionRepository;
        this.cardService = cardService;
        this.billingService = billingService;
        this.userRepository = userRepository;
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


    public List<Transaction> getTransactionsByCardId(Long cardId) {
        return transactionRepository.findByCard_CardId(cardId);
    }

    public Page<Transaction> getCardTransactions(
            Long cardId,
            int page,
            int size,
            TransactionStatus status,
            LocalDateTime start,
            LocalDateTime end) {

        return transactionRepository.findFilteredTransactions(
                cardId,
                status,
                start,
                end,
                PageRequest.of(page, size)
        );
    }

    public Page<Transaction> getUserTransactions(
            Long userId,
            int page,
            int size) {

        return transactionRepository.findByUserId(
                userId,
                PageRequest.of(page, size)
        );
    }

    public List<Transaction> getLatestTransactionsForUser(Long userId){
        return transactionRepository.findByCard_User_UserIdOrderByTransactionDateDesc(userId,PageRequest.of(0,5)).getContent();
    }


}
