package com.makersacademy.acebook.model;

import com.makersacademy.acebook.repository.UserRepository;
import jakarta.persistence.*;
import lombok.Data;

import static java.lang.Boolean.TRUE;

@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private boolean enabled;
    private String myStatus;
    private String profilePhotoUrl;
    private String bio;
    private String firstName;
    private String lastName;


    public User() {this.enabled = TRUE;}

    public User(String username) {
        this.username = username;
        this.enabled = TRUE;
    }

    public User(String username, String myStatus, String profilePhotoUrl, String bio, String firstName, String lastName) {
        this.username = username;
        this.myStatus = myStatus;
        this.profilePhotoUrl = profilePhotoUrl;
        this.bio = bio;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = true;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public String getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(String myStatus) {
        this.myStatus = myStatus;
    }


    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public static User getUserOrElseThrow(UserRepository userRepository, String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
