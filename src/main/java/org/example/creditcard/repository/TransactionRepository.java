package org.example.creditcard.repository;

import org.example.creditcard.model.entity.Transaction;
import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCard(Card card);
    List<Transaction> findByCard_CardId(Long cardId);

    Page<Transaction> findByCard_CardId(Long cardId, Pageable pageable);

    // Card-wise with filters
    @Query("""
        SELECT t FROM Transaction t
        WHERE t.card.cardId = :cardId
        AND (:status IS NULL OR t.status = :status)
        AND (:start IS NULL OR t.transactionDate >= :start)
        AND (:end IS NULL OR t.transactionDate <= :end)
        ORDER BY t.transactionDate DESC
    """)
    Page<Transaction> findFilteredTransactions(
            Long cardId,
            TransactionStatus status,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );

    // ✅ USER-WISE (IMPORTANT)
    @Query("""
        SELECT t FROM Transaction t
        JOIN t.card c
        JOIN c.user u
        WHERE u.userId = :userId
        ORDER BY t.transactionDate DESC
    """)
    Page<Transaction> findByUserId(Long userId, Pageable pageable);

    Page<Transaction> findByCard_User_UserIdOrderByTransactionDateDesc(Long userId,Pageable pageable);
}

