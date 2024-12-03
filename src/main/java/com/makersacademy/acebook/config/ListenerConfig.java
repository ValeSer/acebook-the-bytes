package com.makersacademy.acebook.config;

import com.makersacademy.acebook.listener.FriendshipListener;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @PostConstruct
    public void configureListeners() {
        FriendshipListener.setUserRepository(userRepository);
        FriendshipListener.setNotificationRepository(notificationRepository);
    }
}