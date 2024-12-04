package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.*;
import com.makersacademy.acebook.service.*;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    FriendshipsService friendshipsService;
    @Autowired
    PostsService postsService;
    @Autowired
    PostLikesService postLikesService;
    @Autowired
    CommentsService commentsService;
    @Autowired
    CommentLikesService commentLikesService;

    @GetMapping("/profile/{id}")
    public ModelAndView getUserProfile(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();

        User loggedInUser = userService.getUserProfile();
        Long loggedInUserId = loggedInUser.getId();

        User profileUser = userService.getUserProfileById(id);

        if (profileUser != null) {
            modelAndView.addObject("user", profileUser);
            modelAndView.setViewName("users/profile");
        } else {
            modelAndView.addObject("error", "User not found");
            modelAndView.setViewName("error");
        }

        long numFriends = friendshipsService.numberOfFriendshipsForUser(id);
        Map<Long, User> friends = friendshipsService.getFriendsForUser(id);

        Iterable<Post> posts = postsService.findPostsForUser(id);

        Map<Long, List<Map<String, Object>>> postCommentMapWithDetails = new HashMap<>();
        Map<Long, Boolean> userLikedPostsMap = new HashMap<>();
        Map<Long, Iterable<PostLike>> postLikeMap = new HashMap<>();
        Map<Long, Iterable<CommentLike>> commentLikeMap = new HashMap<>();
        Map<Long, Boolean> userLikedCommentsMap = new HashMap<>();

        for (Post post : posts) {
            Iterable<PostLike> postLikes = postLikesService.getLikesByPostId(post.getId());
            postLikeMap.put(post.getId(), postLikes);

            boolean postIsLikedByUser = postLikesService.userHasLikedPost(post.getId(), id);
            userLikedPostsMap.put(post.getId(), postIsLikedByUser);

            List<Map<String, Object>> commentsWithDetails = commentsService.getCommentsDetailsByPostId(post.getId());
            postCommentMapWithDetails.put(post.getId(), commentsWithDetails);

            Iterable<Comment> comments = commentsService.getCommentsByPostId(post.getId());
            for (Comment comment: comments) {
                Iterable<CommentLike> commentLikes = commentLikesService.getLikesByCommentId(comment.getId());
                commentLikeMap.put(comment.getId(), commentLikes);

                // Get whether comment has been liked by logged in user
                boolean commentIsLikedByUser = commentLikesService.userHasLikedComment(comment.getId(), id);
                userLikedCommentsMap.put(comment.getId(), commentIsLikedByUser);
            }
        }

        modelAndView.addObject("profileUserId", id);
        modelAndView.addObject("profileUser", profileUser);
        modelAndView.addObject("currentUserId", loggedInUserId);
        modelAndView.addObject("currentUser", loggedInUser);
        modelAndView.addObject("numFriends", numFriends);
        modelAndView.addObject("friends", friends.values());
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("postLikes", postLikeMap);
        modelAndView.addObject("likedPosts", userLikedPostsMap);
        modelAndView.addObject("postCommentsDetails", postCommentMapWithDetails);
        modelAndView.addObject("likedComments", userLikedCommentsMap);
        modelAndView.addObject("commentLikes", commentLikeMap);
        modelAndView.addObject("post", new Post());
        modelAndView.addObject("comment", new Comment());

        return modelAndView;
    }

    @PostMapping("/users/update")
    public String updateProfile(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        return "redirect:/profile";
    }

    @PostMapping("/uploadProfilePic")
    public RedirectView uploadProfilePicture(@RequestParam MultipartFile profilePhotoUrl) {
        try {
            String photoUrl = cloudinaryService.uploadImage(profilePhotoUrl);

            userService.updateProfilePictureFromUrl(photoUrl);
            return new RedirectView("/profile");
        } catch (Exception e) {
            return new RedirectView("/profile?error=upload");
        }
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
