package com.makersacademy.acebook.controller;
import com.makersacademy.acebook.model.UserService;
import com.makersacademy.acebook.model.CommentLike;
import com.makersacademy.acebook.model.PostLike;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.CommentLikeRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;


@Controller
public class CommentLikesController {

    @Autowired
    CommentLikeRepository commentLikeRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/comment/{id}/commentlike")
    public RedirectView createPostLike(@PathVariable Long id){
        CommentLike commentLike = new CommentLike();
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();
        commentLike.setUserId(userId);
        commentLike.setCreatedAt(LocalDateTime.now());
        commentLike.setCommentId(id);
        commentLikeRepository.save(commentLike);
        return new RedirectView("/posts");
    }

}
