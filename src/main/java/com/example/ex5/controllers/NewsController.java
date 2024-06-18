package com.example.ex5.controllers;

import com.example.ex5.repo.News;
import com.example.ex5.repo.NewsRepository;
import com.example.ex5.services.NewsService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NewsController {

    /* example: injecting a default value from the application.properties  file */
    @Value( "${demo.coursename}" )
    private String someProperty;

    /* inject via its type the News repo bean - a singleton */
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
        //model.addAttribute("news", new news("noname","noemail"));
        return "add-news";
    }

    /**
     * because we declared a BindingResult parameter, Spring will pass it to the controller
     * and we MUST use it to check for validation errors and catch them
     * @param news
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/addnews")
    public String addNews(@Valid News news, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-news";
        }

        repository.save(news);

        // הפניה מחדש למניעת שליחת טופס כפולה
        return "redirect:/news";
    }

    /** we implemented a POST request for adding a news, as a result
     * if the news accesses the /addnews URL with a GET request, we must redirect him to the main page
     * @param news
     * @param model
     * @return
     */
    @GetMapping("/addnews")
    public String showAddNewsForm(News news, Model model) {
        // redirect to main page
        return "redirect:/";
    }


    /**
     * USING GET HTTP METHODS FOR UPDATING AND DELETING IS NOT A GOOD PRACTICE
     * below you will find the same methods but using POST methods
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {

        News news = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid news Id:" + id));

        // the name "news"  is bound to the VIEW
        model.addAttribute("news", news);
        return "update-news";
    }

    /* same as above but PostMapping
    @PostMapping("/edit")
    public String news(@RequestParam("id") long id, Model model) {

        News news = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));

        // the name "news"  is bound to the VIEW
        model.addAttribute("news", news);
        return "update-news";
    }    */

    @PostMapping("/update/{id}")
    public String updateNews(@PathVariable("id") long id, @Valid News news, BindingResult result, Model model) {
        if (result.hasErrors()) {
            news.setId(id);
            return "update-news";
        }

        repository.save(news);
        model.addAttribute("newsp", repository.findAll());
        return "index";
    }

    @GetMapping("/update/{id}")
    public String updateNewsGet() {
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String News(@RequestParam("id") long id, Model model) {

        // we throw an exception if the news is not found
        // since we don't catch the exception here, it will fall back on an error page (see below)
        News news = repository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid news Id:" + id)
                );
        repository.delete(news);
        model.addAttribute("newsp", repository.findAll());
        return "index";
    }

    /**
     * USING GET HTTP METHODS FOR UPDATING AND DELETING IS NOT A GOOD PRACTICE
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteNewsGet(@PathVariable long id) {
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteNewsGetNoParams() {
        return "redirect:/";
    }

    /**
     * returns the json.html page
     * @param model
     * @return
     */
    @GetMapping(value="/json")
    public String json (Model model) {
        return "json";
    }
    /**
     * a sample controller return the content of the DB in JSON format
     * @return
     */

    /** handling IllegalArgumentException */
    @GetMapping("/error")
    public String error() {
        return "error";
    }

}