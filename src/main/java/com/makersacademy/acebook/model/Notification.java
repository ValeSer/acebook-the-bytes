package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;

@Data
@Entity
@Table(name = "notifications")
public class Notification {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId ;
    private Long receiverId;
    private Long commentId;
    private Long postId;
    private Long friendshipId;
    private Long chatId;


    public Notification() {}

    public Long getSenderId() {
        return this.senderId
    }

//    public Long getUserId(){
//        return this.userId;
//    }
//
//    public void setUserId(Long userId){
//        this.userId = userId;
//    }


}