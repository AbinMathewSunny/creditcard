package org.example.creditcard.model.entity;

import jakarta.persistence.*;
import org.example.creditcard.model.enums.BillStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private LocalDate billingDate;
    private LocalDate dueDate;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    // Many bills → one card
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    // One bill → many payments
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Payment> payments;

    // getters & setters
}
