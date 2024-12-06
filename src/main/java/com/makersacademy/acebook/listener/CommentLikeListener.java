package com.makersacademy.acebook.listener;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.*;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.UserRepository;

import java.util.Optional;

@Component
public class CommentLikeListener {

    private static PostRepository postRepository;
    private static UserRepository userRepository;
    private static NotificationRepository notificationRepository;
    private static CommentRepository commentRepository;

    public static void setUserRepository(UserRepository userRepository) {
        CommentLikeListener.userRepository = userRepository;
    }

    public static void setNotificationRepository(NotificationRepository notificationRepository) {
        CommentLikeListener.notificationRepository = notificationRepository;
    }

    public static void setCommentRepository(CommentRepository commentRepository) {
        CommentLikeListener.commentRepository = commentRepository;
    }

    public static void setPostRepository(PostRepository postRepository) {
        CommentLikeListener.postRepository = postRepository;
    }




    @PrePersist
    public void beforeSave(CommentLike commentLike) {
        System.out.println("Sending like to comment");
    }

    @PostPersist
    public void afterSave(CommentLike commentLike) {

        Notification commentLikeNotification = new Notification();
        commentLikeNotification.setSenderId(commentLike.getUserId());

        Comment comment = commentRepository.findById(commentLike.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment Id not found"));

        Post post = postRepository.findById(comment.getPostId())
                .orElseThrow(() -> new RuntimeException("Post Id not found"));

        commentLikeNotification.setPostId(post.getId());
        commentLikeNotification.setReceiverId(comment.getCommenterId());
        commentLikeNotification.setIsRead(Boolean.FALSE);
        commentLikeNotification.setCommentLikeId(commentLike.getId());
        commentLikeNotification.setType("like");

        User sender = userRepository.findById(commentLike.getUserId())
                .orElseThrow(() -> new RuntimeException("Sender user not found"));

        String firstName = sender.getFirstName();
        String lastName = sender.getLastName();
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        String commentLikeNotifyMessage = firstName + " " + lastName + " liked your comment";
        commentLikeNotification.setContent(commentLikeNotifyMessage);
        commentLikeNotification.setCreatedAt(commentLike.getCreatedAt());
        notificationRepository.save(commentLikeNotification);
    }
}

