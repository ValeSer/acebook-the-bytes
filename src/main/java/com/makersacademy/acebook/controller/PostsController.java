package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.CloudinaryService;
import com.makersacademy.acebook.service.CommentLikesService;
import com.makersacademy.acebook.service.CommentsService;
import com.makersacademy.acebook.service.PostLikesService;
import com.makersacademy.acebook.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    PostLikesService postLikesService;

    @Autowired
    CloudinaryService cloudinaryService;
  
    @Autowired
    CommentLikesService commentLikesService;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts")
    public String index(Model model) {
        Iterable<Post> posts = postsService.getPostsInDateOrder();

//        Map<Long, Iterable<Comment>> postCommentMap = new HashMap<>();
        Map<Long, List<Map<String, Object>>> postCommentMapWithDetails = new HashMap<>();
        Map<Long, User> postUserMap = new HashMap<>();
        Map<Long, Iterable<PostLike>> postLikeMap = new HashMap<>();
        Map<Long, Boolean> userLikedPostsMap = new HashMap<>();
        Map<Long, Iterable<CommentLike>> commentLikeMap = new HashMap<>();
        Map<Long, Boolean> userLikedCommentsMap = new HashMap<>();
        Map<Long, String> formattedTimestamps = new HashMap<>();

        User user = userService.getUserProfile();
        model.addAttribute("currentUserId", user.getId());
        model.addAttribute("currentUser", user);

        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());
        model.addAttribute("comment", new Comment());

        for (Post post: posts) {
            Optional<User> postUser = userRepository.findById(post.getUserId());
            if (postUser.isPresent()) {
                postUserMap.put(post.getUserId(), postUser.get());
            }

            // Get all likes for each post
            Iterable<PostLike> postLikes = postLikesService.getLikesByPostId(post.getId());
            postLikeMap.put(post.getId(), postLikes);

            // Get all comments for each post
            Iterable<Comment> comments = commentsService.getCommentsByPostId(post.getId());
//            postCommentMap.put(post.getId(), comments);

            // Get all comments for each post with details
            List<Map<String, Object>> commentsWithDetails = commentsService.getCommentsDetailsByPostId(post.getId());
            postCommentMapWithDetails.put(post.getId(), commentsWithDetails);

            // Get whether post has been liked by logged in user
            boolean postIsLikedByUser = postLikesService.userHasLikedPost(post.getId(), user.getId());
            userLikedPostsMap.put(post.getId(), postIsLikedByUser);

            // Get formatted timestamps
            LocalDateTime postCreatedAt = post.getCreatedAt();
            LocalDateTime timeNow = LocalDateTime.now();
            Duration timeDiff = Duration.between(postCreatedAt, timeNow);
            String formattedTime;
            if (timeDiff.toMinutes() < 60) {
                long minutes = Math.max(timeDiff.toMinutes() + 1, 1);
                formattedTime = minutes + "m ago";
            } else if (timeDiff.toHours() < 24) {
                formattedTime = timeDiff.toHours() + "h ago";
            } else {
                formattedTime = postCreatedAt.format(DateTimeFormatter.ofPattern("d MMM yyyy h:mm a"));
            }
            formattedTimestamps.put(post.getId(), formattedTime);

            // Get likes for all comments for each post
            for (Comment comment: comments) {
                Iterable<CommentLike> commentLikes = commentLikesService.getLikesByCommentId(comment.getId());
                commentLikeMap.put(comment.getId(), commentLikes);

                // Get whether comment has been liked by logged in user
                boolean commentIsLikedByUser = commentLikesService.userHasLikedComment(comment.getId(), user.getId());
                userLikedCommentsMap.put(comment.getId(), commentIsLikedByUser);
            }
        }

//        model.addAttribute("postComments", postCommentMap);
        model.addAttribute("postCommentsDetails", postCommentMapWithDetails);
        model.addAttribute("postLikes", postLikeMap);
        model.addAttribute("postUserMap", postUserMap);
        model.addAttribute("likedPosts", userLikedPostsMap);
        model.addAttribute("commentLikes", commentLikeMap);
        model.addAttribute("likedComments", userLikedCommentsMap);
        model.addAttribute("postTimestamps", formattedTimestamps);

        return "posts/index";
    }

    @PostMapping("/posts")
    public RedirectView create(@RequestParam String content, @RequestParam(value = "post_photo", required = false) MultipartFile photo) {
        Post post = new Post();
        post.setContent(content);

        String photoUrl = null;
        if(photo != null && !photo.isEmpty()){
            try {
                photoUrl = cloudinaryService.uploadImage(photo);
                post.setPhotoUrl(photoUrl);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
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

    @GetMapping("/post/{id}")
    public String showPost(Model model, @PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User currentUser = userService.getUserProfile();

        boolean postIsLikedByUser = postRepository.existsByUserIdAndId(currentUser.getId(), id);
        long postLikeCount = postLikesService.numberOfLikesForPost(post.getId());
        long postCommentCount = commentsService.numberOfCommentsForPost(post.getId());

        List<Map<String, Object>> postComments = commentsService.getCommentsDetailsByPostId(post.getId());

        Iterable<Comment> comments = commentsService.getCommentsByPostId(post.getId());
        Map<Long, Iterable<CommentLike>> commentLikeMap = new HashMap<>();
        Map<Long, Boolean> userLikedCommentsMap = new HashMap<>();
        for (Comment comment: comments) {
            Iterable<CommentLike> commentLikes = commentLikesService.getLikesByCommentId(comment.getId());
            commentLikeMap.put(comment.getId(), commentLikes);

            boolean commentIsLikedByUser = commentLikesService.userHasLikedComment(comment.getId(), user.getId());
            userLikedCommentsMap.put(comment.getId(), commentIsLikedByUser);
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("comment", new Comment());
        model.addAttribute("postIsLiked", postIsLikedByUser);
        model.addAttribute("postLikeCount", postLikeCount);
        model.addAttribute("postCommentCount", postCommentCount);
        model.addAttribute("postComments", postComments);
        model.addAttribute("commentLikes", commentLikeMap);
        model.addAttribute("userLikedComments", userLikedCommentsMap);
        return "posts/show";
    }
}
