package com.makersacademy.acebook.config;

import com.makersacademy.acebook.listener.*;
import com.makersacademy.acebook.repository.*;
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

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;


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
        CommentLikeListener.setPostRepository(postRepository);

        PostLikeListener.setUserRepository(userRepository);
        PostLikeListener.setNotificationRepository(notificationRepository);
        PostLikeListener.setPostRepository(postRepository);

        MessageListener.setChatRepository(chatRepository);
        MessageListener.setUserRepository(userRepository);
        MessageListener.setNotificationRepository(notificationRepository);

    }
}