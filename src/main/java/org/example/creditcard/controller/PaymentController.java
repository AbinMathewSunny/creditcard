package org.example.creditcard.controller;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.repository.BillRepository;
import org.example.creditcard.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final BillRepository billRepository;

    public PaymentController(
            PaymentService paymentService,
            BillRepository billRepository) {

        this.paymentService = paymentService;
        this.billRepository = billRepository;
    }

    // Pay bill
    @PostMapping("/pay/{billId}")
    public String payBill(@PathVariable Long billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        paymentService.payBill(bill);

        return "redirect:/billing/current/" + bill.getCard().getCardId();
    }
}
