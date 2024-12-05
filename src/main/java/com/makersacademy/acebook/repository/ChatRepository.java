package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
