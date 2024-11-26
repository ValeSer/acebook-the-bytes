package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String photo_url;
    private Timestamp created_at;
    private Long user_id;

    public Post() {}

    public Post(String content, String photo_url, Timestamp created_at, Long user_id) {
        this.content = content;
        this.photo_url = photo_url;
        this.created_at = created_at;
        this.user_id = user_id;
    }
    public String getContent() { return this.content; }
    public void setContent(String content) { this.content = content; }

    public String getPhotoUrl() { return this.photo_url;}
    public void SetPhotoUrl() { this.photo_url = photo_url;}

    public Timestamp getCreatedAt(){ return this.created_at;}
    public void setCreatedAt(){this.created_at = created_at;}

    public Long getUserId(){return this.user_id;}
    public void setUserId(){this.user_id = user_id;}
}
