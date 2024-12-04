package com.makersacademy.acebook.repository;


import com.makersacademy.acebook.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findByReceiverId(Long receiverId);
}
