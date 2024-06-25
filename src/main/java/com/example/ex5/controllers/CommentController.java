package com.example.ex5.controllers;

import com.example.ex5.repo.Comment;
import com.example.ex5.repo.CommentRepository;
import com.example.ex5.repo.News;
import com.example.ex5.repo.NewsRepository;
import com.example.ex5.repo.User;
import com.example.ex5.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/comments")
@Validated
public class CommentController {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    public CommentController(CommentRepository commentRepository, NewsRepository newsRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addComment(@RequestParam Long newsId, @RequestParam String content,
                             @AuthenticationPrincipal UserDetails currentUser,
                             Model model, HttpSession session) {

        // Find the news
        News news = newsRepository.findById(newsId).orElse(null);
        if (news == null) {
            model.addAttribute("error", "News not found");
            return "redirect:/news";
        }

        // Get the authenticated user
        User user = userRepository.findByUsername(currentUser.getUsername());
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "redirect:/news";
        }

        // Create the comment
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setNews(news);
        comment.setUser(user);
        LocalDateTime now = LocalDateTime.now();
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);

        // Save the comment
        commentRepository.save(comment);

        // Retrieve existing or create a new list of comments for this news in session
        @SuppressWarnings("unchecked")
        Map<Long, List<Comment>> commentSession = (Map<Long, List<Comment>>) session.getAttribute("commentSession");
        if (commentSession == null) {
            commentSession = new HashMap<>();
        }

        // Retrieve or create a list of comments for this news
        List<Comment> newsComments = commentSession.getOrDefault(newsId, new ArrayList<>());
        newsComments.add(comment);
        commentSession.put(newsId, newsComments);

        // Update the session attribute
        session.setAttribute("commentSession", commentSession);

        return "redirect:/news";
    }

    @PostMapping("/deletecomment")
    @PreAuthorize("hasRole('USER')")
    public String deleteComment(@RequestParam("id") long id, Model model, Authentication authentication, HttpSession session) {
        // Get the username of the currently authenticated user
        String currentUsername = authentication.getName();

        // Retrieve the map of comments from session
        @SuppressWarnings("unchecked")
        Map<Long, List<Comment>> commentSession = (Map<Long, List<Comment>>) session.getAttribute("commentSession");
        if (commentSession == null) {
            throw new IllegalStateException("Comment session map not found in session");
        }

        // Find the comment by id
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment Id:" + id));

        // Check if the authenticated user is the owner of the comment
        if (!comment.getUser().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You are not authorized to delete this comment");
        }

        // Remove the comment from the session map
        Long newsId = comment.getNews().getId();
        List<Comment> newsComments = commentSession.get(newsId);
        if (newsComments != null) {
            newsComments.removeIf(c -> c.getId() == id);
            if (newsComments.isEmpty()) {
                commentSession.remove(newsId);
            } else {
                commentSession.put(newsId, newsComments);
            }
        }

        // Update the session attribute
        session.setAttribute("commentSession", commentSession);

        // Delete the comment from the database
        commentRepository.delete(comment);

        // Send a message to the WebSocket clients to refresh the page
        template.convertAndSend("/topic/commentDeleted", "comment_deleted");

        // Redirect to the news page after deletion
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
