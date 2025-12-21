package org.example.creditcard.model.entity;

import jakarta.persistence.*;
import org.example.creditcard.model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private BigDecimal paymentAmount;
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    // Many payments → one bill
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    // getters & setters
}
