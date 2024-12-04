package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.service.UserService;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Create a new comment
    @PostMapping("/posts/{postId}/comments/new")
    public RedirectView createComment(@PathVariable Long postId, @RequestParam String content) {
        // Get the post the comment belongs to
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Get the logged-in user
        String username = userService.getAuthenticatedUserEmail();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = user.getId();

        // Create the comment
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPostId(postId);
        comment.setCommenterId(userId);
        comment.setCreatedAt(LocalDateTime.now());

        // Save the comment to the repository
        commentRepository.save(comment);

        return new RedirectView("/posts");  // Redirect to the post's page after comment is created
    }

    @DeleteMapping("/comment/{id}")
    public RedirectView deleteComment(@PathVariable Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();
        if (comment.isPresent() && comment.get().getCommenterId().equals(userId)) {
            commentRepository.deleteById(id);
            return new RedirectView("/posts"); // Redirect to the posts page
        } else {
            throw new RuntimeException("Post not deleted");
        }
    }

//    // Update a comment
//    @PostMapping("/comments/{commentId}/edit")
//    public RedirectView updateComment(@PathVariable Long commentId, @RequestParam String content) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//
//        // Ensure the logged-in user is the owner of the comment
//        String username = userService.getAuthenticatedUserEmail();
//        User user = userRepository.findUserByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!comment.getUser().equals(user)) {
//            throw new RuntimeException("You cannot edit someone else's comment");
//        }
//
//        // Update the comment's content
//        comment.setContent(content);
//        commentRepository.save(comment);
//
//        return new RedirectView("/posts/" + comment.getPost().getId());  // Redirect to the associated post
//    }
//
//    // Delete a comment
//    @PostMapping("/comments/{commentId}/delete")
//    public RedirectView deleteComment(@PathVariable Long commentId) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//
//        // Ensure the logged-in user is the owner of the comment
//        String username = userService.getAuthenticatedUserEmail();
//        User user = userRepository.findUserByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!comment.getUser().equals(user)) {
//            throw new RuntimeException("You cannot delete someone else's comment");
//        }
//
//        // Delete the comment
//        commentRepository.delete(comment);
//
//        return new RedirectView("/posts/" + comment.getPost().getId());  // Redirect to the associated post
//    }
}
