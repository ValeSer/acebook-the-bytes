package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public Optional<User> getUserProfile() {
        return Optional.ofNullable(userService.getUserProfile());
    }

    public String getAuthenticatedUserEmail() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return (String) principal.getAttributes().get("email");  // Email used as the username
    }
}
