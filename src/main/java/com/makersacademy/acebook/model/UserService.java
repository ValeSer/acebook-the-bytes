package com.makersacademy.acebook.model;

import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;


//    get a user profile by their username

    public User getUserProfile() {
        String username = getAuthenticatedUserEmail();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }

    public void updateUser(User user) {
        // Find the user by ID (you can fetch it using the logged-in user's ID)
        Optional<User> existingUser = userRepository.findById(user.getId());

        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setMyStatus(user.getMyStatus());  // Update status
            updatedUser.setBio(user.getBio());  // Update bio
            userRepository.save(updatedUser);  // Save updated user to the database
        }


    }

    public String getAuthenticatedUserEmail() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return (String) principal.getAttributes().get("email");
    }

//    public User updateUserProfile(String username, User updatedUser) {
//        User existingUser = userRepository.findUserByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));

//        if (updatedUser.getFirstName() != null) {
//            existingUser.setFirstName(updatedUser.getFirstName());
//        }
//        if (updatedUser.getLastName() != null) {
//            existingUser.setLastName(updatedUser.getLastName());
//        }
//        if (updatedUser.getBio() != null) {
//            existingUser.setBio(updatedUser.getBio());
//        }
//        if (updatedUser.getMyStatus() != null) {
//            existingUser.setMyStatus(updatedUser.getMyStatus());
//        }
//        if (updatedUser.getProfilePhotoUrl() != null) {
//            existingUser.setProfilePhotoUrl(updatedUser.getProfilePhotoUrl());
//        }
//        return userRepository.save(existingUser);


    }


