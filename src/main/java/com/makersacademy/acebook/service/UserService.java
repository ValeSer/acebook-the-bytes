package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public User getUserProfile() {
        String username = getAuthenticatedUserEmail();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }

    public User getUserProfileById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public String getAuthenticatedUserEmail() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return (String) principal.getAttributes().get("email");
    }

    @Transactional
    public void updateUser(User updatedUser) {
        String username = getAuthenticatedUserEmail();  // Get the currently authenticated user's username (email)

        // Find the existing user by their username
        User existingUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));


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
        userRepository.save(existingUser);
    }

    public Iterable<User> getUsersBySearchTerm(String query) {
        if (query == null || query.trim().isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.searchByName(query);
    }

    @Transactional
    public void updateProfilePictureFromUrl(String profilePhotoUrl) throws IOException {
        String username = getAuthenticatedUserEmail();  // Get current user email
        User existingUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setProfilePhotoUrl(profilePhotoUrl);
        userRepository.save(existingUser);
    }

    private String downloadImage(String profilePhotoUrl) throws IOException {
        // Generate unique file name
        String fileName = UUID.randomUUID().toString() + ".jpg";
        String saveDir = "uploads/profile_pictures/";  // Ensure this directory exists
        File directory = new File(saveDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Fetch and save the image
        URL url = new URL(profilePhotoUrl);
        File savedFile = new File(directory, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(savedFile)) {
            IOUtils.copy(url.openStream(), outputStream);
        }

        return saveDir + fileName;  // Return relative path for use
    }

}



