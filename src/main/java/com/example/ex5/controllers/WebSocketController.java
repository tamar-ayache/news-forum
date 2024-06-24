package com.example.ex5.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/deleteNews")
    @SendTo("/topic/newsDeleted")
    public String deleteNews(String message) throws Exception {
        return HtmlUtils.htmlEscape(message);
    }
}