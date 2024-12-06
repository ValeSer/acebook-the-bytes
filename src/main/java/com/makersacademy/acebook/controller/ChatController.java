package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Chat;
import com.makersacademy.acebook.model.Message;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.ChatRepository;
import com.makersacademy.acebook.repository.MessageRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/chats")
    public String allCurrentUserChats(Model model) {
        User user = userService.getUserProfile();
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();

        List<Chat> getChatsForCurrentUser = chatRepository.findByUser1IdOrUser2Id(userId, userId);
        Map<Long, User> otherPersonsIdsAndChatId = new HashMap<>();
        for (Chat chat : getChatsForCurrentUser){
            if (Objects.equals(chat.getUser1Id(), userId)){
                otherPersonsIdsAndChatId.put(chat.getId(), userService.getUserProfileById(chat.getUser2Id()));
                } else {
                otherPersonsIdsAndChatId.put(chat.getId(), userService.getUserProfileById(chat.getUser1Id()));
                }
            }

        model.addAttribute("post", new Post());
        model.addAttribute("currentUserId", userId);
        model.addAttribute("currentUser", user);
        model.addAttribute("allChats", getChatsForCurrentUser);
        model.addAttribute("allChatsIdsAndOtherPersonIds", otherPersonsIdsAndChatId);
        return "chats/index";
    }


    @GetMapping("/chat/{id}")
    public String showAllMessages(Model model, @PathVariable Long id) {
        User user = userService.getUserProfile();
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();

        Iterable<Message> messages = messageRepository.findByChatIdOrderByCreatedAtAsc(id);
        Message NewMessage = new Message();

        model.addAttribute("post", new Post());
        model.addAttribute("currentUserId", userId);
        model.addAttribute("currentUser", user);
        model.addAttribute("message", NewMessage);
        model.addAttribute("chatId", id);
        model.addAttribute("allMessagesFromBothUsers", messages);
        return "chats/show";
    }

    @PostMapping("/chat/new")
    public RedirectView createOrGetChat(@RequestParam Long profileUserId) {

        String username = userService.getAuthenticatedUserEmail();
        User currentUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long currentUserId = currentUser.getId();

        // Check if a chat already exists
        Chat existingChat = chatRepository.findByUser1IdAndUser2IdOrUser2IdAndUser1Id(currentUserId, profileUserId, currentUserId, profileUserId);
        if (existingChat != null) {
            return new RedirectView("/chat/" + existingChat.getId());
        }

        Chat newChat = new Chat();
        newChat.setUser1Id(currentUserId);
        newChat.setUser2Id(profileUserId);
        chatRepository.save(newChat);

        // Redirect to the new chat
        return new RedirectView("/chat/" + newChat.getId());
    }
}
