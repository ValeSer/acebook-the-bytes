package com.makersacademy.acebook.repository;


import com.makersacademy.acebook.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
