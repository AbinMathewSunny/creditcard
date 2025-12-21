package org.example.creditcard.model.entity;

import jakarta.persistence.*;
import org.example.creditcard.model.enums.TicketStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "support_ticket")
public class SupportTicket {
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(LocalDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    private String issueDescription;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    private LocalDateTime createdDate;
    private LocalDateTime resolvedDate;

    // Many tickets → one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // getters & setters
}
