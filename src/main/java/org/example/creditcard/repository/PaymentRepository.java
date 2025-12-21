package org.example.creditcard.repository;

import org.example.creditcard.model.entity.Payment;
import org.example.creditcard.model.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBill(Bill bill);
}
