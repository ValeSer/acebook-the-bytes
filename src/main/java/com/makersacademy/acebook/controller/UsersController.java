package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ModelAndView getUserProfile() {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserProfile();
        if (user != null) {
            modelAndView.addObject("user", user);
            modelAndView.setViewName("users/profile");
        } else {
            modelAndView.addObject("error", "User not found");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    // POST request for updating bio and status
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Get the currently authenticated user
        User currentUser = userService.getUserProfile();

        if (currentUser != null) {
            // Update the user's bio and status
            currentUser.setBio(user.getBio());
            currentUser.setMyStatus(user.getMyStatus());

            // Save the updated user to the database
            userService.updateUser(currentUser);

            // Add success message to be displayed on profile page
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Unable to update profile.");
        }

        return "redirect:/profile"; // Redirect back to profile page
    }

    @GetMapping("/home")
    public String getHomepage(Model model) {
        model.addAttribute("message", "Welcome to the homepage!");
        return "home"; // Return the homepage view (home.html)
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    // Utility method to get the authenticated user's email
    public String getAuthenticatedUserEmail() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return (String) principal.getAttributes().get("email");  // Email used as the username
    }
}
