package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
