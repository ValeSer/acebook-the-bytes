package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Iterable<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByReceiverId(userId);
    }
}
