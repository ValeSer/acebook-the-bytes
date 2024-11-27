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


    public User(String username, String myStatus, String profilePhotoUrl, String bio, String firstName, String lastName) {
        this.username = username;
        this.myStatus = myStatus;
        this.profilePhotoUrl = profilePhotoUrl;
        this.bio = bio;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = TRUE;
    }


    public static User getUserOrElseThrow(UserRepository userRepository, String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }
}

