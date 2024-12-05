package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.dto.FriendRequestDto;
import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.service.UserService;
import com.makersacademy.acebook.repository.FriendshipRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        User user = userService.getUserProfile();
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();

        Iterable<Friendship> friendRequests = friendshipRepository.findByReceiverIdAndStatusAndSenderIdNot(
                userId, "pending", userId
        );

        List<FriendRequestDto> friendRequestDtos = new ArrayList<>();
        for (Friendship friendship : friendRequests) {
            User sender = userRepository.findById(friendship.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender not found"));
            friendRequestDtos.add(new FriendRequestDto(
                    friendship.getId(),
                    sender.getId(),
                    sender.getFirstName(),
                    sender.getLastName(),
                    friendship.getStatus()
            ));
        }

        model.addAttribute("post", new Post());
        model.addAttribute("currentUser", user);
        model.addAttribute("friendRequests", friendRequestDtos);
        return "friends/index";
    }


    @PostMapping("/friends/new")
    public RedirectView toggleFriendshipRequest(@RequestParam Long receiverId, @RequestParam String page){
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();
        Friendship existingFriendship = friendshipRepository.findBySenderIdAndReceiverId(userId, receiverId);
        if (existingFriendship != null) {
            String status = existingFriendship.getStatus();

            if ("pending".equals(status) || "accepted".equals(status)) {
                friendshipRepository.delete(existingFriendship);
            }
        } else {
            Friendship friendshipRequest = new Friendship();
            friendshipRequest.setSenderId(userId);
            friendshipRequest.setStatus("pending");
            friendshipRequest.setReceiverId(receiverId);
            friendshipRequest.setCreatedAt(LocalDateTime.now());
            friendshipRepository.save(friendshipRequest);
        }

        if ("profile".equals(page)) {
            return new RedirectView("/profile/" + userId);
        } else {
            return new RedirectView("/search");
        }
    }

    @PostMapping("/friends/accept/{id}")
    public String acceptFriendRequest(@PathVariable Long id) {
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friendship request not found"));

        friendship.setStatus("accepted");
        friendshipRepository.save(friendship);

        return "redirect:/friends";
    }

    @PostMapping("/friends/reject/{id}")
    public String rejectFriendRequest(@PathVariable Long id) {
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friendship request not found"));

        friendship.setStatus("rejected");
        friendshipRepository.save(friendship);

        return "redirect:/friends";
    }
}