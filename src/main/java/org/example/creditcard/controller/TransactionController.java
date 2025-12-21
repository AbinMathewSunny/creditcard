package org.example.creditcard.controller;

import org.example.creditcard.model.entity.Card;
import org.example.creditcard.service.CardService;
import org.example.creditcard.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final CardService cardService;

    public TransactionController(
            TransactionService transactionService,
            CardService cardService) {

        this.transactionService = transactionService;
        this.cardService = cardService;
    }

    // Show transaction form
    @GetMapping("/new/{cardId}")
    public String showTransactionForm(
            @PathVariable Long cardId,
            Model model) {

        Card card = cardService.getCardById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        model.addAttribute("card", card);
        return "transaction-form";
    }

    // Submit transaction
    @PostMapping("/create")
    public String makeTransaction(
            @RequestParam Long cardId,
            @RequestParam String merchantName,
            @RequestParam BigDecimal amount) {

        Card card = cardService.getCardById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        transactionService.makeTransaction(card, merchantName, amount);

        return "redirect:/cards/view/" + cardId;
    }
}
