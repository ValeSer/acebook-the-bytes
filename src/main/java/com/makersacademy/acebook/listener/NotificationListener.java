package com.makersacademy.acebook.listener;
import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserService;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PostPersist;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
//import jakarta.persistence.PreUpdate;
//import jakarta.persistence.PostUpdate;
//import jakarta.persistence.PreRemove;
//import jakarta.persistence.PostRemove;

public class NotificationListener {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationListener(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @PrePersist
    public void beforeSave(Friendship friendship) {
        System.out.println("Sending friendship request");
    }

    @PostPersist
    public void afterSave(Friendship friendship) {
        System.out.println("Friendship sent");
        Notification friendshipRequestNotification = new Notification();
        friendshipRequestNotification.setSenderId(friendship.getSenderId());
        friendshipRequestNotification.setReceiverId(friendship.getReceiverId());
        friendshipRequestNotification.setIsRead(Boolean.FALSE);
        friendshipRequestNotification.setFriendshipId(friendship.getId());
        friendshipRequestNotification.setType("Friendship request");

        User sender = userRepository.findById(friendship.getSenderId())
            .orElseThrow(() -> new RuntimeException("Sender user not found"));

        String firstName = sender.getFirstName();
        String lastName = sender.getLastName();
        System.out.println("################# START #####################");
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println("################# END ########################");
        String friendShipNotifyMessage = firstName + " " + lastName + " sent a friendship request";
        friendshipRequestNotification.setContent(friendShipNotifyMessage);
        friendshipRequestNotification.setCreatedAt(friendship.getCreatedAt());
        notificationRepository.save(friendshipRequestNotification);
    }
}


