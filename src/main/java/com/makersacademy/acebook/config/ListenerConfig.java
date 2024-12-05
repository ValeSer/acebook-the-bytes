package com.makersacademy.acebook.config;

import com.makersacademy.acebook.listener.CommentLikeListener;
import com.makersacademy.acebook.listener.CommentListener;
import com.makersacademy.acebook.listener.FriendshipListener;
import com.makersacademy.acebook.listener.PostLikeListener;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.NotificationRepository;
import com.makersacademy.acebook.repository.PostRepository;
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

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostConstruct
    public void configureListeners() {
        FriendshipListener.setUserRepository(userRepository);
        FriendshipListener.setNotificationRepository(notificationRepository);

        CommentListener.setPostRepository(postRepository);
        CommentListener.setUserRepository(userRepository);
        CommentListener.setNotificationRepository(notificationRepository);

        CommentLikeListener.setUserRepository(userRepository);
        CommentLikeListener.setNotificationRepository(notificationRepository);
        CommentLikeListener.setCommentRepository(commentRepository);

        PostLikeListener.setUserRepository(userRepository);
        PostLikeListener.setNotificationRepository(notificationRepository);
        PostLikeListener.setPostRepository(postRepository);
    }
}