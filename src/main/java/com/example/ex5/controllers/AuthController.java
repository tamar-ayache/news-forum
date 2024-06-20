package com.example.ex5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("role")
public class AuthController {

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
        if ("admin".equals(username) && "admin".equals(password)) {
            model.addAttribute("isAdmin", true);
            model.addAttribute("role", "ADMIN");
            return "redirect:/news";
        } else if ("user".equals(username) && "userpass".equals(password)) {
            model.addAttribute("role", "ROLE_ANONYMOUS");
            return "redirect:/news";
        } else {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/login";
    }
}
