package com.makersacademy.acebook.controller;
import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.CommentLikeRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.CommentLikesService;
import com.makersacademy.acebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;


@Controller
public class CommentLikesController {

    @Autowired
    CommentLikeRepository commentLikeRepository;

    @Autowired
    CommentLikesService commentLikesService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/comment/{id}/like")
    public ResponseEntity<Void> createCommentLike(@PathVariable Long id){
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();

        if (!commentLikesService.userHasLikedComment(id, userId)) {
            CommentLike commentLike = new CommentLike();
            commentLike.setUserId(userId);
            commentLike.setCreatedAt(LocalDateTime.now());
            commentLike.setCommentId(id);
            commentLikeRepository.save(commentLike);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comment/{id}/unlike")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable Long id) {
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();
        Optional<CommentLike> commentLike = commentLikesService.getLikeByCommentIdAndUserId(id, userId);
        if (commentLike.isPresent() && commentLike.get().getUserId().equals(userId)) {
            Long commentLikeId = commentLike.get().getId();
            commentLikeRepository.deleteById(commentLikeId);
            return ResponseEntity.ok().build();
        } else{
            throw new RuntimeException("Comment like not deleted");
        }
    }

}
