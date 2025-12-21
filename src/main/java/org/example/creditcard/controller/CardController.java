package org.example.creditcard.controller;

import org.example.creditcard.model.entity.Card;
import org.example.creditcard.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * CUSTOMER: Apply (request) a card
     */
    @PostMapping("/apply")
    public String applyCard(@ModelAttribute Card card) {
        cardService.requestCard(card);
        return "redirect:/cards/all";
    }

    /**
     * BANKER / ADMIN: Approve a card
     */
    @PostMapping("/approve/{cardId}")
    public String approveCard(@PathVariable Long cardId) {
        cardService.approveCard(cardId);
        return "redirect:/cards/all";
    }

    /**
     * BANKER / ADMIN: Update credit limit
     */
    @PostMapping("/update-credit-limit")
    public String updateCreditLimit(
            @RequestParam Long cardId,
            @RequestParam Double creditLimit) {

        Card card = cardService.getCardById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setCreditLimit(java.math.BigDecimal.valueOf(creditLimit));
        cardService.updateCard(card);

        return "redirect:/cards/details/" + cardId;
    }

    /**
     * View card details
     */
    @GetMapping("/details/{cardId}")
    public String getCardDetails(@PathVariable Long cardId, Model model) {
        Card card = cardService.getCardById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        model.addAttribute("card", card);
        return "card-details";
    }

    /**
     * BANKER / ADMIN: View all cards
     */
    @GetMapping("/all")
    public String getAllCards(Model model) {
        model.addAttribute("cards", cardService.getAllCards());
        return "card-list";
    }
}
