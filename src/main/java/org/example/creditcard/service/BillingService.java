package org.example.creditcard.service;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.enums.BillStatus;
import org.example.creditcard.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BillingService {

    private final BillRepository billRepository;

    public BillingService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill getOrCreateActiveBill(Card card) {

        return billRepository
                .findByCardAndStatus(card, BillStatus.PENDING)
                .orElseGet(() -> createNewBill(card));
    }

    private Bill createNewBill(Card card) {
        Bill bill = new Bill();
        bill.setCard(card);
        bill.setBillingDate(LocalDate.now());
        bill.setDueDate(LocalDate.now().plusDays(20));
        bill.setTotalAmount(java.math.BigDecimal.ZERO);
        bill.setStatus(BillStatus.PENDING);

        return billRepository.save(bill);
    }

    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }
}
