package org.example.creditcard.controller;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.model.entity.Card;
import org.example.creditcard.service.BillingService;
import org.example.creditcard.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        Bill bill = billingService.getActiveBill(card);
        model.addAttribute("card", card); // ✅ ADD THIS LINE
        model.addAttribute("bill", bill);
        return "bill-details";
    }

    @PostMapping("/generate/all")
    public String generateAllBills() {

        billingService.generateBillsForAllCards();

        return "redirect:/banker/dashboard";
    }

    @GetMapping("/history/{cardId}")
    public String billHistory(
            @PathVariable Long cardId,
            Model model) {

        List<Bill> bills = billingService.getBillsForCard(cardId);

        model.addAttribute("bills", bills);
        model.addAttribute("cardId", cardId);

        return "billing-history";
    }


}
