package org.example.creditcard.controller;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.model.entity.Card;
import org.example.creditcard.service.BillingService;
import org.example.creditcard.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;
    private final CardService cardService;

    public BillingController(
            BillingService billingService,
            CardService cardService) {

        this.billingService = billingService;
        this.cardService = cardService;
    }

    // View current bill
    @GetMapping("/current/{cardId}")
    public String viewCurrentBill(
            @PathVariable Long cardId,
            Model model) {

        Card card = cardService.getCardById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        Bill bill = billingService.getOrCreateActiveBill(card);
        model.addAttribute("card", card); // ✅ ADD THIS LINE
        model.addAttribute("bill", bill);
        return "bill-details";
    }
}
