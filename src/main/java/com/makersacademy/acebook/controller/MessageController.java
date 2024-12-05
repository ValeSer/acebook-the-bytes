package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.service.MessageService;
import com.makersacademy.acebook.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MessageController {

    private final MessageService messageService;

    // Constructor for injecting MessageService
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public String showMessagesPage(Principal principal, Model model) {
        String username = principal.getName();  // Extract username from logged-in user

        // Fetch messages or any other logic that needs the username
        List<Message> messages = messageService.getMessagesByUsername(username);
        model.addAttribute("messages", messages);

        return "messages";  // Return the messages view
    }


    // Send a new message from the logged-in user to the receiver
    @PostMapping("/send")
    public String sendMessage(@RequestParam("receiverUsername") String receiverUsername,
                              @RequestParam("content") String content,
                              Principal principal) {
        String senderUsername = principal.getName();  // Get the logged-in user's username (email)

        // Initialize the timestamp when the message is sent
        LocalDateTime timestamp = LocalDateTime.now();

        // Send the message via the MessageService
        messageService.sendMessage(senderUsername, receiverUsername, content, timestamp);

        return "redirect:/messages?username=" + receiverUsername;  // Redirect to the conversation with the receiver
    }

    // Get all conversation partners for the logged-in user
    @GetMapping("/conversations")
    public String getAllConversations(Model model, Principal principal) {
        String username = principal.getName();  // Get the logged-in user's username (email)

        // Fetch all conversation partners for the logged-in user
        List<String> partners = messageService.getAllConversationPartners(username);

        // Add the partners to the model to display in the view
        model.addAttribute("partners", partners);

        return "conversations";  // This would be your conversations list view page
    }
}
