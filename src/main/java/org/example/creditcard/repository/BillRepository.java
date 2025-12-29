package org.example.creditcard.repository;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByCard(Card card);

    Optional<Bill> findByCardAndStatus(Card card, org.example.creditcard.model.enums.BillStatus status);

    List<Bill> findByCardOrderByBillingDateDesc(Card card);

}
