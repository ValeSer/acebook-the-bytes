package com.makersacademy.acebook.model;

import com.makersacademy.acebook.listener.CommentListener;
import com.makersacademy.acebook.listener.FriendshipListener;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(CommentListener.class)
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long postId;
    private Long commenterId;
    private LocalDateTime createdAt;

    // Default constructor
    public Comment() {}

    // Constructor for creating comment with content, user and post
    public Comment(String content, Long postId, Long commenterId, LocalDateTime createdAt) {
        this.content = content;
        this.postId = postId;
        this.commenterId = commenterId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCommenterId() {
        return this.commenterId;
    }

    public void setCommenterId(Long commenterId) {
        this.commenterId = commenterId;
    }

    public Long getPostId() {
        return this.postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
