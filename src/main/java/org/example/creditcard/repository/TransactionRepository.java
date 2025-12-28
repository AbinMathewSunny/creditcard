package org.example.creditcard.repository;

import org.example.creditcard.model.entity.Transaction;
import org.example.creditcard.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCard(Card card);

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM Transaction t
    WHERE t.card = :card AND t.billed = false""")
    BigDecimal sumUnbilledTransactions(@Param("card") Card card);

    @Modifying
    @Query("""
    UPDATE Transaction t
    SET t.billed = true
    WHERE t.card = :card AND t.billed = false""")
    void markTransactionsAsBilled(@Param("card") Card card);

}
