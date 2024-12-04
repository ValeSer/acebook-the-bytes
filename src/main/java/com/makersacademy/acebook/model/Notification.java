package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import javax.management.NotificationListener;
import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;

@Data
@Entity
@EntityListeners(NotificationListener.class)
@Table(name = "NOTIFICATIONS")
public class Notification {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId ;
    private Long receiverId;
    private Long commentId;
    private Long postId;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private Long friendshipId;
    private Long chatId;
    private String type;
    private String content;

    public Notification() {}

    public Long getId() {
        return this.id;
    }

    public Long getSenderId() {
        return this.senderId;
    }

    public Long getReceiverId() {
        return this.receiverId;
    }

    public Long getCommentId() {
        return this.commentId;
    }

    public Long getPostId() {
        return this.postId;
    }

    public Boolean getIsRead() {
        return this.isRead;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Long getFriendshipId() {
        return this.friendshipId;
    }

    public Long getChatId() {
        return this.chatId;
    }

    public String getType() {
        return this.type;
    }

    public String getContent() {
        return this.content;
    }

    // Setters
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setFriendshipId(Long friendshipId) {
        this.friendshipId = friendshipId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }



}