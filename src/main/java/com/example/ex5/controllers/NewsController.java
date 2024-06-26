package com.example.ex5.controllers;

import com.example.ex5.repo.News;
import com.example.ex5.repo.NewsRepository;
import com.example.ex5.services.NewsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller for handling news-related requests.
 */
@Controller
public class NewsController {

    @Value("${demo.coursename}")
    private String someProperty;

    @Autowired
    private NewsRepository repository;
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private NewsService newsService;

    /**
     * Constructor for NewsController.
     *
     * @param newsService the news service to be used
     */
    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Displays the home page with a list of news articles.
     *
     * @param modelAndView the model and view to be used
     * @return the model and view for the home page
     */
    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        List<News> newsList = repository.findAll();
        modelAndView.addObject("newsp", newsList);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    /**
     * Displays the news page with a list of news articles.
     *
     * @param news the news object
     * @param model the model to be used
     * @return the view name for the news page
     */
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

    /**
     * Displays the comment form.
     *
     * @param news the news object
     * @param model the model to be used
     * @param session the HTTP session
     * @return the view name for the comment form
     */
    @GetMapping("/showComment")
    public String showCommentForm(News news, Model model, HttpSession session) {
        model.addAttribute("myComments", session.getAttribute("commentSession"));
        return "user-comment";
    }

    /**
     * Displays the sign-up form for news.
     *
     * @param news the news object
     * @param model the model to be used
     * @return the view name for the sign-up form
     */
    @GetMapping("/addnews")
    public String showSignUpForm(News news, Model model) {
        return "add-news";
    }

    /**
     * Adds a news article.
     *
     * @param news the news object to be added
     * @param result the binding result
     * @param model the model to be used
     * @return redirect to the news page
     */
    @PostMapping("/addnews")
    @PreAuthorize("hasRole('ADMIN')")
    public String addNews(@Valid News news, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-news";
        }
        // Send a message to the WebSocket clients to refresh the page
        template.convertAndSend("/topic/newsDeleted", "news_deleted");
        repository.save(news);
        return "redirect:/news";
    }

//    /**
//     * Displays the update form for a news article.
//     *
//     * @param id the ID of the news article to be updated
//     * @param model the model to be used
//     * @return the view name for the update form
//     */
//    @GetMapping("/edit/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String showUpdateForm(@PathVariable("id") long id, Model model) {
//        News news = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
//        model.addAttribute("news", news);
//        return "update-news";
//    }
//
//    /**
//     * Updates a news article.
//     *
//     * @param id the ID of the news article to be updated
//     * @param news the updated news object
//     * @param result the binding result
//     * @param model the model to be used
//     * @return redirect to the news page
//     */
//    @PostMapping("/update/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String updateNews(@PathVariable("id") long id, @Valid News news, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            news.setId(id);
//            return "update-news";
//        }
//        // Send a message to the WebSocket clients to refresh the page
//        template.convertAndSend("/topic/newsDeleted", "news_deleted");
//        repository.save(news);
//        return "redirect:/news";
//    }

    /**
     * Deletes a news article.
     *
     * @param id the ID of the news article to be deleted
     * @param model the model to be used
     * @return redirect to the news page
     */
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteNews(@RequestParam("id") long id, Model model) {
        News news = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid news Id:" + id));
        repository.delete(news);
        // Send a message to the WebSocket clients to refresh the page
        template.convertAndSend("/topic/newsDeleted", "news_deleted");
        return "redirect:/news";
    }

    /**
     * Displays the JSON view.
     *
     * @param model the model to be used
     * @return the view name for the JSON view
     */
    @GetMapping("/json")
    public String json(Model model) {
        return "json";
    }

    /**
     * Displays the error view.
     *
     * @return the view name for the error view
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
