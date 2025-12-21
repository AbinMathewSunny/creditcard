package org.example.creditcard.model.entity;

import jakarta.persistence.*;
import org.example.creditcard.model.enums.Role;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<SupportTicket> getSupportTickets() {
        return supportTickets;
    }

    public void setSupportTickets(List<SupportTicket> supportTickets) {
        this.supportTickets = supportTickets;
    }

    @Column(nullable = false)
    private String password; // hashed

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    // One user → many cards
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Card> cards;

    // One user → many support tickets
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SupportTicket> supportTickets;

    // getters & setters
}
