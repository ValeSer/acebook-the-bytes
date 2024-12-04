package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.PostLike;
import com.makersacademy.acebook.repository.PostLikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostsService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    public Iterable<Post> getPostsInDateOrder() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Iterable<Post> findPostsForUser(Long userId) {
        return postRepository.getPostsByUserIdOrderByCreatedAtDesc(userId);
    }
}
