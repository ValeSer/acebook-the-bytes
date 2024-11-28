package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    public Post() {}

    public Post(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt;
    }
    public String getContent() { return this.content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
