package com.example.ex5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if ("admin".equals(username) && "adminpass".equals(password)) {
            session.setAttribute("role", "ADMIN");
            return "redirect:/messages";
        } else if ("user".equals(username) && "userpass".equals(password)) {
            session.setAttribute("role", "USER");
            return "redirect:/messages";
        } else {
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
