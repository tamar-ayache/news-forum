package com.example.ex5.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * Controller for handling WebSocket messages.
 */
@Controller
public class WebSocketController {

    /**
     * Handles messages sent to /deleteNews and broadcasts them to /topic/newsDeleted.
     *
     * @param message the incoming message
     * @return the escaped message to be broadcast
     * @throws Exception if an error occurs
     */
    @MessageMapping("/deleteNews")
    @SendTo("/topic/newsDeleted")
    public String deleteNews(String message) throws Exception {
        return HtmlUtils.htmlEscape(message);
    }
}
