package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Find messages between two specific usernames (emails)
    List<Message> findBySenderUsernameAndReceiverUsername(String senderUsername, String receiverUsername);

    // Find messages where the user is either the sender or receiver by their username
    List<Message> findBySenderUsernameOrReceiverUsername(String senderUsername, String receiverUsername);
}
