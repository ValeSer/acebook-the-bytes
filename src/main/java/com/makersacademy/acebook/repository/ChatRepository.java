package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUser1IdOrUser2Id(Long userId, Long userId2);
}
