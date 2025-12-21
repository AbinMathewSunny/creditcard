package org.example.creditcard.repository;

import org.example.creditcard.model.entity.SupportTicket;
import org.example.creditcard.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    List<SupportTicket> findByUser(User user);
}
