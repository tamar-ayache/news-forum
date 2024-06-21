//package com.example.ex5.controllers;
//
//import com.example.ex5.repo.User;
//import com.example.ex5.repo.UserRepository;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/signup")
//@Validated
//public class SignupController {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @GetMapping
//    public String signupForm(Model model) {
//        model.addAttribute("user", new User()); // יצירת אובייקט User ריק לצורך הטופס
//        return "signup"; // שם של קובץ ה-HTML של טופס ההרשמה
//    }
//
//    @PostMapping
//    public String signupSubmit(@ModelAttribute @Valid User user, Model model) {
//        // בדיקה אם השדה username ריק
//        if (user.getUsername() == null || user.getUsername().isEmpty()) {
//            model.addAttribute("error", "Username is mandatory");
//            return "signup";
//        }
//
//        // בדיקה אם המשתמש כבר קיים
//        User existingUser = userRepository.findByUsername(user.getUsername());
//        if (existingUser != null) {
//            // טיפול במקרה של משתמש קיים כבר
//            model.addAttribute("error", "User already exists");
//            return "signup";
//        }
//
//        // הצפנת הסיסמה לפני השמירה במסד הנתונים
////        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(user.getPassword());
//        user.setRole("USER");
//
//        // שמירת המשתמש החדש במסד הנתונים
//        userRepository.save(user);
//
//        // הפניה לעמוד ראשי אחרי ההרשמה
//        return "redirect:/news";
//    }
//}
