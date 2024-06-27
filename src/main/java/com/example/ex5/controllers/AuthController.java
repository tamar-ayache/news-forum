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
    /**
     * Constructor for AuthController
     * @param userDetailsService the user details service to be used
     */
    public AuthController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    /**
     * Displays the login form.
     *
     * @param model the model to be used
     * @param error optional error parameter to display login error
     * @return the login view
     */
    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(name = "error", required = false) String error) {
        model.addAttribute("role", "");
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "login";
    }
    /**
     * Handles login attempts.
     *
     * @param username the username provided
     * @param password the password provided
     * @param model the model to be used
     * @return redirect to the news page if successful, or back to the login page with error if unsuccessful
     */
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
    /**
     * Handles user logout.
     *
     * @param status the session status to be completed
     * @return redirect to the login page
     */
    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/login";
    }
}
