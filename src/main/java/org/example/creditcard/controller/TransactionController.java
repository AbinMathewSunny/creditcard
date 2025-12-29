package org.example.creditcard.controller;

import org.example.creditcard.model.entity.Card;
import org.example.creditcard.model.entity.Transaction;
import org.example.creditcard.model.enums.TransactionStatus;
import org.example.creditcard.service.CardService;
import org.example.creditcard.service.TransactionService;
import org.springframework.data.domain.Page;
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

//    @GetMapping("/history/{cardId}")
//    public String viewTransactionHistory(
//            @PathVariable Long cardId,
//            Model model) {
//
//        Card card = cardService.getCardById(cardId)
//                .orElseThrow(() -> new RuntimeException("Card not found"));
//
//        model.addAttribute("card", card);
//        model.addAttribute("transactions",  transactionService.getTransactionsByCardId(cardId));
//
//        return "transaction-history";
//    }

    @GetMapping("/history/{cardId}")
    public String viewTransactionHistory(
            @PathVariable Long cardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            @RequestParam(required = false) TransactionStatus status,
            Model model) {

        Card card = cardService.getCardById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        var transactions = transactionService.getCardTransactions(
                cardId, page, size, status, null, null
        );

        model.addAttribute("card", card);
        model.addAttribute("transactions", transactions);
        model.addAttribute("status", status);

        return "transaction-history";
    }

    @GetMapping("/user/{userId}")
    public String viewUserTransactions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            Model model) {

        Page<Transaction> transactions = transactionService.getUserTransactions(userId, page, size);
        model.addAttribute("transactions", transactions);
        model.addAttribute("userId", userId);

        return "transaction-history";
    }


}
