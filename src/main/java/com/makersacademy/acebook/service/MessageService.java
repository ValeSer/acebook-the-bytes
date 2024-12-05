package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.model.Message;
import com.makersacademy.acebook.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    // Constructor for injecting dependencies
    public MessageService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    // Fetch user by username (which is the email)
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    // Get conversation between two users by their usernames (email)
    public List<Message> getConversation(String senderUsername, String receiverUsername) {
        // Get users by username (email)
        User sender = getUserByUsername(senderUsername);
        User receiver = getUserByUsername(receiverUsername);

        // Retrieve the conversation between sender and receiver
        return messageRepository.findBySenderUsernameAndReceiverUsername(senderUsername, receiverUsername);
    }

    // Get all conversation partners for a specific user
    public List<String> getAllConversationPartners(String username) {
        // Fetch messages where this user is either sender or receiver
        List<Message> messages = messageRepository.findBySenderUsernameOrReceiverUsername(username, username);

        // Extract usernames of the partners from the messages
        return messages.stream()
                .map(message -> message.getSenderUsername().equals(username) ? message.getReceiverUsername() : message.getSenderUsername())
                .distinct()
                .toList();
    }

    // Send a new message
    public void sendMessage(String senderUsername, String receiverUsername, String content, LocalDateTime timestamp) {
        // Get the sender and receiver usernames (emails)
        User sender = getUserByUsername(senderUsername);
        User receiver = getUserByUsername(receiverUsername);

        // Create a new message
        Message message = new Message(senderUsername, receiverUsername, content, timestamp);

        // Save the message
        messageRepository.save(message);
    }

    public List<Message> getMessagesByUsername(String username) {
        return messageRepository.findBySenderUsernameOrReceiverUsername(username, username);
    }
}
