package org.example.creditcard.service;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.enums.BillStatus;
import org.example.creditcard.model.enums.CardStatus;
import org.example.creditcard.repository.BillRepository;
import org.example.creditcard.repository.CardRepository;
import org.example.creditcard.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BillingService {

    private final BillRepository billRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    public BillingService(
            BillRepository billRepository,
            CardRepository cardRepository,
            TransactionRepository transactionRepository) {

        this.billRepository = billRepository;
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Generates bills for ALL eligible cards.
     * Triggered by BANKER (one button).
     */
    @Transactional
    public void generateBillsForAllCards() {

        List<Card> cards = cardRepository.findAll();

        for (Card card : cards) {

            // Skip cards already blocked (already billed)
            if (card.getStatus() == CardStatus.BLOCKED) {
                continue;
            }

            // Calculate total of unbilled transactions
            BigDecimal total =
                    transactionRepository.sumUnbilledTransactions(card);

            // No spending → no bill
            if (total.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            // Create bill
            Bill bill = new Bill();
            bill.setCard(card);
            bill.setBillingDate(LocalDate.now());
            bill.setDueDate(LocalDate.now().plusDays(20));
            bill.setTotalAmount(total);
            bill.setStatus(BillStatus.PENDING);

            billRepository.save(bill);

            // Mark transactions as billed
            transactionRepository.markTransactionsAsBilled(card);

            // Block card until bill is paid
            card.setStatus(CardStatus.BLOCKED);
            cardRepository.save(card);
        }
    }

    /**
     * Fetch active bill for a card (used by USER to view/pay bill).
     */
    public Bill getActiveBill(Card card) {
        return billRepository
                .findByCardAndStatus(card, BillStatus.PENDING)
                .orElse(null);
    }

    public Bill getBillById(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    public List<Bill> getBillsForCard(Long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        return billRepository.findByCardOrderByBillingDateDesc(card);
    }

}
