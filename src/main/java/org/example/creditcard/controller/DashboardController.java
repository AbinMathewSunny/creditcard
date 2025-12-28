package org.example.creditcard.controller;

import org.example.creditcard.model.entity.User;
import org.example.creditcard.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow();

        model.addAttribute("user", user);
        model.addAttribute("cards", user.getCards());

        return "dashboard";
    }
}
