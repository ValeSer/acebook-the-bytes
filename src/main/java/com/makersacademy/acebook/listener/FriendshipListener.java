package com.makersacademy.acebook.listener;
import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PostPersist;

import org.springframework.stereotype.Component;
//import jakarta.persistence.PreUpdate;
//import jakarta.persistence.PostUpdate;
//import jakarta.persistence.PreRemove;
//import jakarta.persistence.PostRemove;

@Component
public class FriendshipListener {


    private static UserRepository userRepository;
    private static NotificationRepository notificationRepository;

    public static void setUserRepository(UserRepository userRepository) {
        FriendshipListener.userRepository = userRepository;
    }

    public static void setNotificationRepository(NotificationRepository notificationRepository) {
        FriendshipListener.notificationRepository = notificationRepository;
    }

    @PrePersist
    public void beforeSave(Friendship friendship) {
        System.out.println("Sending friendship request");
    }

    @PostPersist
    public void afterSave(Friendship friendship) {

        Notification friendshipRequestNotification = new Notification();
        friendshipRequestNotification.setSenderId(friendship.getSenderId());
        friendshipRequestNotification.setReceiverId(friendship.getReceiverId());
        friendshipRequestNotification.setIsRead(Boolean.FALSE);
        friendshipRequestNotification.setFriendshipId(friendship.getId());
        friendshipRequestNotification.setType("friend_request");

        User sender = userRepository.findById(friendship.getSenderId())
            .orElseThrow(() -> new RuntimeException("Sender user not found"));

        String firstName = sender.getFirstName();
        String lastName = sender.getLastName();
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        String friendShipNotifyMessage = firstName + " " + lastName + " sent you a friendship request";
        friendshipRequestNotification.setContent(friendShipNotifyMessage);
        friendshipRequestNotification.setCreatedAt(friendship.getCreatedAt());
        notificationRepository.save(friendshipRequestNotification);
    }

}


