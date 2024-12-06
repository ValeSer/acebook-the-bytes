package com.makersacademy.acebook.listener;

import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.Message;
import com.makersacademy.acebook.model.Notification;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.Chat;
import com.makersacademy.acebook.repository.ChatRepository;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MessageListener {
    private static UserRepository userRepository;
    private static NotificationRepository notificationRepository;
    private static ChatRepository chatRepository;

    public static void setUserRepository(UserRepository userRepository) {
        MessageListener.userRepository = userRepository;
    }

    public static void setNotificationRepository(NotificationRepository notificationRepository) {
        MessageListener.notificationRepository = notificationRepository;
    }
    public static void setChatRepository(ChatRepository chatRepository) {
        MessageListener.chatRepository = chatRepository;
    }

    @PrePersist
    public void beforeSave(Message message) {
        System.out.println("Sending message");
    }

    @PostPersist
    public void afterSave(Message message) {
        Chat chat = chatRepository.findById(message.getChatId())
            .orElseThrow(() -> new RuntimeException("Sender user not found"));

        Long receiverId = (Objects.equals(chat.getUser1Id(), message.getSenderId())) ? chat.getUser2Id() : chat.getUser1Id();
        Notification messageNotification = new Notification();
        messageNotification.setSenderId(message.getSenderId());
        messageNotification.setReceiverId(receiverId);
        messageNotification.setIsRead(Boolean.FALSE);
        messageNotification.setMessageId(message.getId());
        messageNotification.setChatId(message.getChatId());
        messageNotification.setType("message");

        User sender = userRepository.findById(message.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender user not found"));

        String firstName = sender.getFirstName();
        String lastName = sender.getLastName();
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        String messageNotifyMessage = firstName + " " + lastName + " sent you a message";
        messageNotification.setContent(messageNotifyMessage);
        messageNotification.setCreatedAt(message.getCreatedAt());
        notificationRepository.save(messageNotification);
    }


}
