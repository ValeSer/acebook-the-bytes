package com.makersacademy.acebook.model;
import com.makersacademy.acebook.listener.CommentLikeListener;
import com.makersacademy.acebook.listener.PostLikeListener;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(PostLikeListener.class)
@Table(name = "POST_LIKES")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long postId;
    private LocalDateTime createdAt;

    public PostLike() {}

    public PostLike(Long userId, Long postId, LocalDateTime createdAt){
        this.userId = userId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    public Long getId() { return this.id; };

    public Long getUserId(){
        return this.userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getPostId(){
        return this.postId;
    }

    public void setPostId(Long postId){
        this.postId = postId;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

}
