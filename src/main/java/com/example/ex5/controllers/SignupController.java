package com.example.ex5.controllers;

import com.example.ex5.repo.User;
import com.example.ex5.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling user signup requests.
 */
@Controller
@RequestMapping("/signup")
@Validated
public class SignupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for SignupController.
     *
     * @param userRepository the user repository to be used
     * @param passwordEncoder the password encoder to be used
     */
    @Autowired
    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Displays the signup form.
     *
     * @param model the model to be used
     * @return the view name for the signup form
     */
    @GetMapping
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    /**
     * Handles the signup form submission.
     *
     * @param user the user object containing signup details
     * @param model the model to be used
     * @param ownerCode optional owner code for admin role
     * @return redirect to the login page if successful, or back to the signup form with error message if unsuccessful
     */
    @PostMapping
    public String signupSubmit(@ModelAttribute @Valid User user, Model model, @RequestParam(required = false) String ownerCode) {

        // Check if username is empty
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            model.addAttribute("error", "Username is mandatory");
            return "signup";
        }

        // Check if the user already exists
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("error", "User already exists");
            return "signup";
        }

        // Check if user selected admin role and if the provided owner code is valid
        if ("ADMIN".equals(user.getRole())) {
            if (ownerCode == null || !ownerCode.equals("1234")) {
                model.addAttribute("error", "Invalid owner code for admin role");
                return "signup";
            }
        }

        // Set the user role based on the form selection
        user.setRole("ADMIN".equals(user.getRole()) ? "ADMIN" : "USER");

        // Encrypt the password before saving to the database
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save the new user to the database
        userRepository.save(user);

        // Redirect to the login page after successful signup
        return "redirect:/login";
    }
}
