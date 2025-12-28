package org.example.creditcard.controller;

import org.example.creditcard.model.entity.User;
import org.example.creditcard.model.enums.Role;
import org.example.creditcard.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {

        user.setRole(Role.CUSTOMER);
        userRepository.save(user);

        return "redirect:/login";
    }
}
