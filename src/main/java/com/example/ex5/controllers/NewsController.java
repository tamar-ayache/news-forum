package com.example.ex5.controllers;

import com.example.ex5.repo.Message;
import com.example.ex5.repo.MessageRepository;
import com.example.ex5.repo.News;
import com.example.ex5.repo.NewsRepository;
import com.example.ex5.services.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
import java.util.List;

@Controller
public class NewsController {

    @Value("${demo.coursename}")
    private String someProperty;

    @Autowired
    private NewsRepository repository;

    @Autowired
    private NewsService newsService;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/news")
    public String main(News news, Model model) {
        model.addAttribute("course", someProperty);
        List<News> newsList = repository.findAll();
        model.addAttribute("newsp", newsList);

        // Fetch authenticated user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")); // Check if user has ROLE_ADMIN

        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);

        return "index";
    }

    @GetMapping("/signupnews")
    public String showSignUpForm(News news, Model model) {
        return "add-news";
    }

    @PostMapping("/addnews")
    public String addNews(@Valid News news, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-news";
        }
        repository.save(news);
        return "redirect:/news";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        News news = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        model.addAttribute("news", news);
        return "update-news";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateNews(@PathVariable("id") long id, @Valid News news, BindingResult result, Model model) {
        if (result.hasErrors()) {
            news.setId(id);
            return "update-news";
        }
        repository.save(news);
        return "redirect:/news";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteNews(@RequestParam("id") long id, Model model) {
        News news = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        repository.delete(news);
        return "redirect:/news";
    }

    @GetMapping("/json")
    public String json(Model model) {
        return "json";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/messages")
    public String viewMessages(Model model) {
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "messages";
    }

    @PostMapping("/messages/add")
    public String addMessage(@RequestParam String content, HttpSession session) {
        Message message = new Message();
        message.setContent(content);
        message.setApproved(false);
        messageRepository.save(message);
        return "redirect:/messages";
    }

    @PostMapping("/messages/approve")
    public String approveMessage(@RequestParam Long id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if ("ADMIN".equals(role)) {
            Message message = messageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + id));
            message.setApproved(true);
            messageRepository.save(message);
        }
        return "redirect:/messages";
    }
}
