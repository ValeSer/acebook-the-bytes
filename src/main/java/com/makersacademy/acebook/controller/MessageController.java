package com.makersacademy.acebook.controller;


import com.makersacademy.acebook.model.Message;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.MessageRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@Controller
public class MessageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MessageRepository messageRepository;

    @PostMapping("/chat/{id}")
    public RedirectView sendMessage(@PathVariable Long id, @RequestParam String messageContent){
        Message newMessage = new Message();
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();
        newMessage.setSenderId(userId);
        newMessage.setCreatedAt(LocalDateTime.now());
        newMessage.setChatId(id);
        newMessage.setMessage(messageContent);
        messageRepository.save(newMessage);
        return new RedirectView("/chat/" + id);
    }


}
