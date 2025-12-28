package org.example.creditcard.service;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.entity.Payment;
import org.example.creditcard.model.enums.BillStatus;
import org.example.creditcard.model.enums.CardStatus;
import org.example.creditcard.model.enums.PaymentStatus;
import org.example.creditcard.repository.BillRepository;
import org.example.creditcard.repository.CardRepository;
import org.example.creditcard.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillRepository billRepository;
    private final CardRepository cardRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            BillRepository billRepository,
            CardRepository cardRepository) {

        this.paymentRepository = paymentRepository;
        this.billRepository = billRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public void payBill(Long billId) {

        // 1️⃣ Fetch bill
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        // 2️⃣ Prevent double payment
        if (bill.getStatus() == BillStatus.PAID) {
            throw new RuntimeException("Bill already paid");
        }

        // 3️⃣ Create payment record
        Payment payment = new Payment();
        payment.setBill(bill);
        payment.setPaymentAmount(bill.getTotalAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);

        // 4️⃣ Mark bill as PAID
        bill.setStatus(BillStatus.PAID);
        billRepository.save(bill);

        // 5️⃣ Reactivate card and reset credit
        Card card = bill.getCard();
        card.setStatus(CardStatus.ACTIVE);
        card.setAvailableBalance(card.getCreditLimit());
        cardRepository.save(card);
    }
}
