package org.example.creditcard.service;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.entity.Payment;
import org.example.creditcard.model.enums.BillStatus;
import org.example.creditcard.model.enums.PaymentStatus;
import org.example.creditcard.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CardService cardService;

    public PaymentService(
            PaymentRepository paymentRepository,
            CardService cardService) {

        this.paymentRepository = paymentRepository;
        this.cardService = cardService;
    }

    public Payment payBill(Bill bill) {

        Card card = bill.getCard();

        // Restore full balance
        card.setAvailableBalance(card.getCreditLimit());
        cardService.updateCard(card);

        // Update bill
        bill.setStatus(BillStatus.PAID);

        // Create payment record
        Payment payment = new Payment();
        payment.setBill(bill);
        payment.setPaymentAmount(bill.getTotalAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        return paymentRepository.save(payment);
    }
}
