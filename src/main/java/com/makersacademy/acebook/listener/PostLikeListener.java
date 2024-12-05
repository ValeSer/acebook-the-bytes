package com.makersacademy.acebook.listener;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

@Component
public class PostLikeListener {
    private static UserRepository userRepository;
    private static NotificationRepository notificationRepository;
    private static PostRepository postRepository;

    public static void setUserRepository(UserRepository userRepository) {
        PostLikeListener.userRepository = userRepository;
    }

    public static void setNotificationRepository(NotificationRepository notificationRepository) {
        PostLikeListener.notificationRepository = notificationRepository;
    }

    public static void setPostRepository(PostRepository postRepository) {
        PostLikeListener.postRepository = postRepository;
    }

    @PrePersist
    public void beforeSave(PostLike postLike) {
        System.out.println("Sending like to post");
    }

    @PostPersist
    public void afterSave(PostLike postLike) {

        Notification postLikeNotification = new Notification();
        postLikeNotification.setSenderId(postLike.getUserId());

        Post post = postRepository.findById(postLike.getPostId())
                .orElseThrow(() -> new RuntimeException("Post Id not found"));

        postLikeNotification.setReceiverId(post.getUserId());
        postLikeNotification.setIsRead(Boolean.FALSE);
        postLikeNotification.setPostLikeId(postLike.getId());
        postLikeNotification.setType("new_post_like");

        User sender = userRepository.findById(post.getUserId())
                .orElseThrow(() -> new RuntimeException("Sender user not found"));

        String firstName = sender.getFirstName();
        String lastName = sender.getLastName();
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        String postLikeNotifyMessage = firstName + " " + lastName + " liked your post";
        postLikeNotification.setContent(postLikeNotifyMessage);
        postLikeNotification.setCreatedAt(postLike.getCreatedAt());
        notificationRepository.save(postLikeNotification);
    }
}
