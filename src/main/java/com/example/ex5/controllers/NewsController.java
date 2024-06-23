package com.example.ex5.controllers;


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
import org.springframework.web.servlet.ModelAndView;

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
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        List<News> newsList = repository.findAll();
        modelAndView.addObject("newsp", newsList);
        modelAndView.setViewName("index");
        return modelAndView;
    }
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
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_USER")); // Check if user has ROLE_USER

        model.addAttribute("username", username);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isUser", isUser);

        return "index";
    }

    @GetMapping("/signupnews")
    public String showSignUpForm(News news, Model model) {
        return "add-news";
    }

    @PostMapping("/addnews")
    @PreAuthorize("hasRole('ADMIN')")
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


}
