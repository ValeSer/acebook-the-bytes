package com.makersacademy.acebook.model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "COMMENT_LIKES")

public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long commentId;
    private LocalDateTime createdAt;

    public CommentLike() {}

    public CommentLike(Long userId, Long commentId, LocalDateTime createdAt) {
        this.userId = userId;
        this.commentId = commentId;
        this.createdAt = createdAt;
    }

    public Long getId(){
        return this.id;
    }

    public Long getUserId(){
        return this.userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getCommentId(){
        return this.commentId;
    }

    public void setCommentId(Long commentId){
        this.commentId = commentId;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }


}
