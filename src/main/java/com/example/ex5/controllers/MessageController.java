package com.example.ex5.controllers;

import com.example.ex5.repo.Message;
import com.example.ex5.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

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

    @PostMapping("/messages/delete")
    public String deleteMessage(@RequestParam Long id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if ("ADMIN".equals(role)) {
            Message message = messageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid message Id:" + id));
            messageRepository.delete(message);
        }
        return "redirect:/messages";
    }
}
