package org.example.creditcard.controller;


import org.example.creditcard.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

   private final TransactionService transactionService;

   public UserController(TransactionService transactionService){
       this.transactionService=transactionService;
   }

    @GetMapping("/dashboard")
    public String customerDashboard(Model model) {

//        User user = userService.findByUsername(principal.getName());
//
//        model.addAttribute("user", user);

        model.addAttribute("recentTransactions",transactionService.getLatestTransactionsForUser(1L));

//        model.addAttribute("totalLimit", cardService.getTotalLimit(user));
//        model.addAttribute("availableBalance", cardService.getAvailableBalance(user));
//        model.addAttribute("currentDue", billService.getCurrentDue(user));

        return "user-dashboard";
    }

}
