package org.example.creditcard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/banker")
public class BankerController {

    @GetMapping("/dashboard")
    public String bankerDashboard() {
        return "banker-dashboard";
    }
}
