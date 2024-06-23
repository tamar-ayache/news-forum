package com.example.ex5.controllers;

import com.example.ex5.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("role")
public class AuthController {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(name = "error", required = false) String error) {
        model.addAttribute("role", "");
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            userDetailsService.loadUserByUsername(username);
            model.addAttribute("role", "ROLE_ANONYMOUS");
            return "redirect:/news";
        } catch (Exception e) {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/login";
    }
}
