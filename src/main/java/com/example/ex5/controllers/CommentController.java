package com.example.ex5.controllers;

import com.example.ex5.repo.Comment;
import com.example.ex5.repo.CommentRepository;
import com.example.ex5.repo.News;
import com.example.ex5.repo.NewsRepository;
import com.example.ex5.repo.User;
import com.example.ex5.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/comments")
@Validated
public class CommentController {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository, NewsRepository newsRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addComment(@RequestParam Long newsId, @RequestParam String content, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        News news = newsRepository.findById(newsId).orElse(null);
        if (news == null) {
            model.addAttribute("error", "News not found");
            return "redirect:/news";
        }

        User user = userRepository.findByUsername(currentUser.getUsername());
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "redirect:/news";
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setNews(news);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return "redirect:/news";
    }

    @PostMapping("/edit")
    public String editComment(@RequestParam Long commentId, @RequestParam String content, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            model.addAttribute("error", "Comment not found");
            return "redirect:/news";
        }

        User user = userRepository.findByUsername(currentUser.getUsername());
        if (user == null || !user.equals(comment.getUser())) {
            model.addAttribute("error", "User not authorized to edit this comment");
            return "redirect:/news";
        }

        comment.setContent(content);
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return "redirect:/news";
    }
}
