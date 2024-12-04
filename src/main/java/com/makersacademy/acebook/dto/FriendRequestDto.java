package com.makersacademy.acebook.dto;

public class FriendRequestDto {
    private Long friendshipId;
    private Long senderId;
    private String senderFirstName;
    private String senderLastName;
    private String status;


    public FriendRequestDto(Long friendshipId, Long senderId, String senderFirstName, String senderLastName, String status) {
        this.friendshipId = friendshipId;
        this.senderId = senderId;
        this.senderFirstName = senderFirstName;
        this.senderLastName = senderLastName;
        this.status = status;
    }


    public Long getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Long friendshipId) {
        this.friendshipId = friendshipId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
