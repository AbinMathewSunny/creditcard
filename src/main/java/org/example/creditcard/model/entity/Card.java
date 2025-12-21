package org.example.creditcard.model.entity;

import jakarta.persistence.*;
import org.example.creditcard.model.enums.CardStatus;
import org.example.creditcard.model.enums.CardType;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(unique = true, nullable = false)
    private String cardNumber;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private BigDecimal creditLimit;
    private BigDecimal availableBalance;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    // Many cards → one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // One card → many transactions
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    // One card → many bills
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Bill> bills;

    // getters & setters
}
