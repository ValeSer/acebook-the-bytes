package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.PostLike;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostLikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class PostsService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    CloudinaryService cloudinaryService;

    public Iterable<Post> getPostsInDateOrder() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Iterable<Post> findPostsForUser(Long userId) {
        return postRepository.getPostsByUserIdOrderByCreatedAtDesc(userId);
    }

    public void createPost(String content, MultipartFile photo) {
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

        postRepository.save(post);
    }
}
