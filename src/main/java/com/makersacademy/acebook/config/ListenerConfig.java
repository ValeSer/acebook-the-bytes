package com.makersacademy.acebook.config;

import com.makersacademy.acebook.listener.NotificationListener;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class ListenerConfig {
//    @Bean
//    public NotificationListener notificationListener(UserRepository userRepository, NotificationRepository notificationRepository) {
//        NotificationListener.setNotificationRepository(userRepository, notificationRepository);
//        return new NotificationListener();
//    }
//}

    @Configuration
    public class ListenerConfig {
        @Bean
        public NotificationListener notificationListener( NotificationRepository notificationRepository) {
            return new NotificationListener(notificationRepository);
        }
    }

