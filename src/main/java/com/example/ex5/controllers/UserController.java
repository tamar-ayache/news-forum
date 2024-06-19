package com.example.ex5.controllers;

import com.example.ex5.repo.User;
import com.example.ex5.repo.UserRepository;
import com.example.ex5.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Value("${demo.coursename}")
    private String someProperty;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService userService;

    @GetMapping("/userlog")
    public String main(User user, Model model) {
        model.addAttribute("course", someProperty);
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    @GetMapping("/signuplog")
    public String showSignUpForm(User user, Model model) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        repository.save(user);
        return "redirect:/userlog";
    }

    @PostMapping("/update/log/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }
        repository.save(user);
        return "redirect:/userlog";
    }

    @PostMapping("/delete/log")
    public String deleteUser(@RequestParam("id") long id, Model model) {
        User user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repository.delete(user);
        return "redirect:/userlog";
    }

    @GetMapping("/update/log/{id}")
    public String updateUserGet() {
        return "redirect:/";
    }

    @GetMapping("/delete/log/{id}")
    public String deleteUserGet(@PathVariable long id) {
        return "redirect:/";
    }

    @GetMapping("/delete/log")
    public String deleteUserGetNoParams() {
        return "redirect:/";
    }

    @GetMapping(value="/json/log")
    public String json (Model model) {
        return "json";
    }

    @GetMapping("/error/log")
    public String error() {
        return "error";
    }
}
