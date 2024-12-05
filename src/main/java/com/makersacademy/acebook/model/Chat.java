package com.makersacademy.acebook.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CHATS")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_1_id")
    private Long user1Id;

    @Column(name = "user_2_id")
    private Long user2Id;

    public Chat(){}

    public Chat(Long user1Id, Long user2Id){
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public Long getId(){
        return this.id;
    }

    public Long getSenderId(){
        return this.user1Id;
    }

    public void setSenderId(Long user1Id){
        this.user1Id = user1Id;
    }


    public Long getReceiverId(){
        return this.user2Id;
    }

    public void setReceiverId(Long user2Id){
        this.user2Id = user2Id;
    }
}


