package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;

import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.NotificationsService;
import com.makersacademy.acebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NotificationsController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationsService notificationsService;

    @GetMapping("/notifications")
    public String viewNotifications(Model model) {
        User user = userService.getUserProfile();
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();

        Iterable<Notification> allNotificationsForUser = notificationsService.getNotificationsForUser(userId);

        model.addAttribute("notifications", allNotificationsForUser);
        model.addAttribute("currentUserId", userId);
        model.addAttribute("currentUser", user);
        model.addAttribute("post", new Post());
        return "notifications/index";
    }
}
