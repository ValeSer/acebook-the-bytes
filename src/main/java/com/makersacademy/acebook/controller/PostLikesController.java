package com.makersacademy.acebook.controller;
import com.makersacademy.acebook.model.PostLike;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserService;
import com.makersacademy.acebook.repository.PostLikeRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.PostLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class PostLikesController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    PostLikesService postLikesService;


    @PostMapping("/post/{id}/postlike")
    public ResponseEntity<Void> createPostLike(@PathVariable Long id){
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();

        if (!postLikesService.userHasLikedPost(id, userId)) {
            PostLike postLike = new PostLike();
            postLike.setUserId(userId);
            postLike.setCreatedAt(LocalDateTime.now());
            postLike.setPostId(id);
            postLikeRepository.save(postLike);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{id}/postunlike")
    public ResponseEntity<Void> deletePostLike(@PathVariable Long id){
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();
        Optional<PostLike> postLike = postLikesService.getLikeByPostIdAndUserId(id, userId);
        if (postLike.isPresent() && postLike.get().getUserId().equals(userId)) {
            Long postLikeId = postLike.get().getId();
            postLikeRepository.deleteById(postLikeId);
            return ResponseEntity.ok().build();
        } else {
            throw new RuntimeException("Post like not deleted");
        }
    }
}

