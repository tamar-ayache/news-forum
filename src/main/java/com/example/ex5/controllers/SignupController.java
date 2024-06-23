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

@Controller
@RequestMapping("/signup")
@Validated
public class SignupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping
    public String signupSubmit(@ModelAttribute @Valid User user, Model model, @RequestParam(required = false) String ownerCode) {

        // בדיקה אם השדה username ריק
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            model.addAttribute("error", "Username is mandatory");
            return "signup";
        }

        // בדיקה אם המשתמש כבר קיים
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("error", "User already exists");
            return "signup";
        }

        // בדיקה אם המשתמש בחר להיות אדמין ואם הוזן קוד הבעלים תקין
        if ("ADMIN".equals(user.getRole())) {
            if (ownerCode == null || !ownerCode.equals("1234")) {
                model.addAttribute("error", "Invalid owner code for admin role");
                return "signup";
            }
        }

        // הגדרת תפקיד המשתמש בהתאם לבחירתו בטופס
        user.setRole("ADMIN".equals(user.getRole()) ? "ADMIN" : "USER");

        // הצפנת הסיסמה לפני השמירה במסד הנתונים
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // שמירת המשתמש החדש במסד הנתונים
        userRepository.save(user);

        // הפניה לעמוד ראשי אחרי ההרשמה
        return "redirect:/login";
    }
}
