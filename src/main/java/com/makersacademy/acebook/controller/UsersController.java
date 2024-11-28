package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserService;
import com.makersacademy.acebook.repository.UserRepository;
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
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    UserRepository userRepository;

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

    @PostMapping("/users/update")
    public String updateProfile(@ModelAttribute User user, RedirectAttributes redirectAttributes) {


        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        return "redirect:/profile";
    }



    @GetMapping("/users/after-login")
    public RedirectView afterLogin() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String username = (String) principal.getAttributes().get("email");
        userRepository
                .findUserByUsername(username)
                .orElseGet(() -> userRepository.save(new User(username)));

        return new RedirectView("/posts");
    }


//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            new SecurityContextLogoutHandler().logout(request, response, authentication);
//        }
//        return "redirect:/";

//
//
//    public String getAuthenticatedUserEmail() {
//        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//        return (String) principal.getAttributes().get("email");
//    }
}
