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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

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
        Iterable<Notification> allUnreadNotificationsForUser = notificationsService.getUnreadNotificationsForUser(userId);

        Map<Long, String> redirectMap = new HashMap<>();
        for (Notification notification : allUnreadNotificationsForUser) {
            String redirectUrl = "";
            String type = notification.getType();

            if (type.equals("like") || type.equals("comment")) {
                redirectUrl += "/post/" + notification.getPostId();
            } else if (type.equals("friend_request")) {
                redirectUrl += "/friends";
            } else if (type.equals("message")) {
                redirectUrl += "/chats" + notification.getChatId();
            } else {
                redirectUrl += "/posts";
            }

            redirectMap.put(notification.getId(), redirectUrl);
        }

        model.addAttribute("notifications", allNotificationsForUser);
        model.addAttribute("unreadNotifications", allUnreadNotificationsForUser);
        model.addAttribute("currentUserId", userId);
        model.addAttribute("currentUser", user);
        model.addAttribute("redirectMap", redirectMap);
        model.addAttribute("post", new Post());
        return "notifications/index";
    }

    @PostMapping("/notifications/{id}")
    public RedirectView readNotification(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
        return new RedirectView("/notifications");
    }
}
