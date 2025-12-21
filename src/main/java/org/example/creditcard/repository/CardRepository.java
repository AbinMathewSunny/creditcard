package org.example.creditcard.repository;

import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.entity.User;
import org.example.creditcard.model.enums.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByUser(User user);
    List<Card> findByStatus(CardStatus status);

}

