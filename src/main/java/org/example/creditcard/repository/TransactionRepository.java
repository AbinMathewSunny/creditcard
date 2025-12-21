package org.example.creditcard.repository;

import org.example.creditcard.model.entity.Transaction;
import org.example.creditcard.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCard(Card card);
}
