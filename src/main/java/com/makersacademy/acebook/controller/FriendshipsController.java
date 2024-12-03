package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserService;
import com.makersacademy.acebook.repository.FriendshipRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class FriendshipsController {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/friends")
    public String friendRequests(Model model) {
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();
        Iterable<Friendship> friendRequests = friendshipRepository.findByReceiverIdAndStatus(userId, "pending");
        model.addAttribute("friendRequests", friendRequests);
        return "friends/index";
    }


    @PostMapping("/search")
    public RedirectView toggleFriendshipRequest(@RequestParam Long receiverId){
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();

        Friendship existingFriendship = friendshipRepository.findBySenderIdAndReceiverId(userId, receiverId);
        if (existingFriendship != null) {
            friendshipRepository.delete(existingFriendship);
        } else {
            Friendship friendshipRequest = new Friendship();
            friendshipRequest.setSenderId(userId);
            friendshipRequest.setStatus("pending");
            friendshipRequest.setReceiverId(receiverId);
            friendshipRequest.setCreatedAt(LocalDateTime.now());
            friendshipRepository.save(friendshipRequest);
        }
            return new RedirectView("/search");
    }

}