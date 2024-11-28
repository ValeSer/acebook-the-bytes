package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "POSTS")
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String photoUrl;
    private LocalDateTime createdAt;
    private Long userId;

    public Post() {}

    public Post(String content, LocalDateTime createdAt, Long userId) {
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Post(String content, String photoUrl, LocalDateTime createdAt, Long userId) {
        this.content = content;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
