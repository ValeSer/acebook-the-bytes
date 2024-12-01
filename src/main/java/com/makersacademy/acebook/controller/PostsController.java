package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserService;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.CommentsService;
import com.makersacademy.acebook.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class PostsController {
    @Autowired
    PostRepository repository;

    @Autowired
    PostsService postsService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentsService commentsService;

    @GetMapping("/posts")
    public String index(Model model) {
        Iterable<Post> posts = postsService.getPostsInDateOrder();

        Map<Long, Iterable<Comment>> postCommentMap = new HashMap<>();

        User user = userService.getUserProfile();
        model.addAttribute("currentUserId", user.getId());
        model.addAttribute("currentUser", user);

        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());
        model.addAttribute("comment", new Comment());
        for (Post post: posts) {
            Iterable<Comment> comments = commentsService.getCommentsByPostId(post.getId());
            postCommentMap.put(post.getId(),comments);
        }
        model.addAttribute("postComments", postCommentMap);
        return "posts/index";
    }

    @PostMapping("/posts")
    public RedirectView create(@RequestParam String content, @RequestParam String photoUrl) {
        Post post = new Post();
        post.setContent(content);
        post.setPhotoUrl(photoUrl);
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long id = userDetails.getId();
        post.setUserId(id);
        post.setCreatedAt(LocalDateTime.now());
        repository.save(post);
        return new RedirectView("/posts");
    }

    @DeleteMapping("/post/{id}")
    public RedirectView deletePost(@PathVariable Long id) {
        Optional<Post> post = repository.findById(id);
        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();
        if (post.isPresent() && post.get().getUserId().equals(userId)) {
            repository.deleteById(id);
            return new RedirectView("/posts"); // Redirect to the posts page
        } else {
            throw new RuntimeException("Post not deleted");
        }
    }
}
