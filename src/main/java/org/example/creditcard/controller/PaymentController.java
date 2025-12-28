package org.example.creditcard.controller;

import org.example.creditcard.model.entity.Bill;
import org.example.creditcard.service.BillingService;
import org.example.creditcard.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final BillingService billingService;

    public PaymentController(
            PaymentService paymentService,
            BillingService billingService) {

        this.paymentService = paymentService;
        this.billingService = billingService;
    }

    // 🔹 Show payment page
    @GetMapping("/{billId}")
    public String showPaymentPage(
            @PathVariable Long billId,
            Model model) {

        Bill bill = billingService.getBillById(billId);

        if (bill.getTotalAmount().signum() == 0) {
            throw new RuntimeException("Nothing to pay");
        }

        model.addAttribute("bill", bill);
        return "bill-payment"; // your payment.html
    }

    // 🔹 Process payment
    @PostMapping("/pay/{billId}")
    public String payBill(@PathVariable Long billId) {

        paymentService.payBill(billId);

        return "redirect:/payments/success";
    }

    // 🔹 Payment success page
    @GetMapping("/success")
    public String paymentSuccess() {
        return "payment-success";
    }
}
