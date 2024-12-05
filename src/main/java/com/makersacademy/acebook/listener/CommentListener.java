package com.makersacademy.acebook.listener;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentListener {

    private static UserRepository userRepository;
    private static NotificationRepository notificationRepository;
    private static PostRepository postRepository;

    public static void setUserRepository(UserRepository userRepository) {
        CommentListener.userRepository = userRepository;
    }

    public static void setNotificationRepository(NotificationRepository notificationRepository) {
        CommentListener.notificationRepository = notificationRepository;
    }

    public static void setPostRepository(PostRepository postRepository) {
        CommentListener.postRepository = postRepository;
    }

    @PrePersist
    public void beforeSave(Comment comment) {
        System.out.println("Sending comment");
    }

    @PostPersist
    public void afterSave(Comment comment) {


        Notification commentNotification = new Notification();
        commentNotification.setSenderId(comment.getCommenterId());

        Post post = postRepository.findById(comment.getPostId())
            .orElseThrow(() -> new RuntimeException("Post Id not found"));

        commentNotification.setReceiverId((post.getUserId()));
        commentNotification.setIsRead(Boolean.FALSE);
        commentNotification.setCommentId(comment.getId());
        commentNotification.setType("new_comment");

        User sender = userRepository.findById(comment.getCommenterId())
                .orElseThrow(() -> new RuntimeException("Sender user not found"));

        String firstName = sender.getFirstName();
        String lastName = sender.getLastName();
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        String commentNotifyMessage = firstName + " " + lastName + " commented on your post";
        commentNotification.setContent(commentNotifyMessage);
        commentNotification.setCreatedAt(comment.getCreatedAt());
        notificationRepository.save(commentNotification);
    }

}
