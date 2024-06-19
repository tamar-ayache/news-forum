package com.example.ex5.controllers;

import com.example.ex5.repo.News;
import com.example.ex5.repo.NewsRepository;
import com.example.ex5.services.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Controller
public class NewsController {

    @Value("${demo.coursename}")
    private String someProperty;

    @Autowired
    private NewsRepository repository;

    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public String main(News news, Model model) {
        model.addAttribute("course", someProperty);
        List<News> newsList = repository.findAll();
        model.addAttribute("newsp", newsList);
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

    @GetMapping("/addnews")
    public String showAddNewsForm(News news, Model model) {
        return "redirect:/";
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

    @GetMapping("/update/{id}")
    public String updateNewsGet() {
        return "redirect:/";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteNews(@RequestParam("id") long id, Model model) {
        News news = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        repository.delete(news);
        return "redirect:/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNewsGet(@PathVariable long id) {
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteNewsGetNoParams() {
        return "redirect:/";
    }

    @GetMapping(value = "/json")
    public String json(Model model) {
        return "json";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
