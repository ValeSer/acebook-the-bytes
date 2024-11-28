package com.makersacademy.acebook.model;

import com.makersacademy.acebook.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;


    public User getUserProfile() {
        String username = getAuthenticatedUserEmail();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }


    public String getAuthenticatedUserEmail() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return (String) principal.getAttributes().get("email");
    }

    @Transactional
    public User updateUser(User updatedUser) {
        String username = getAuthenticatedUserEmail();  // Get the currently authenticated user's username (email)

        // Find the existing user by their username
        User existingUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields if provided (not null or empty)
        if (updatedUser.getBio() != null && !updatedUser.getBio().isEmpty()) {
            existingUser.setBio(updatedUser.getBio());
        }
        if (updatedUser.getMyStatus() != null && !updatedUser.getMyStatus().isEmpty()) {
            existingUser.setMyStatus(updatedUser.getMyStatus());
        }
        if (updatedUser.getProfilePhotoUrl() != null && !updatedUser.getProfilePhotoUrl().isEmpty()) {
            existingUser.setProfilePhotoUrl(updatedUser.getProfilePhotoUrl());
        }

        // Save the updated user profile to the database
        return userRepository.save(existingUser);


    }

}



