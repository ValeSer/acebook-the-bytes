package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.PostLike;
import com.makersacademy.acebook.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostLikesService {
    @Autowired
    PostLikeRepository postLikeRepository;

    public Iterable<PostLike> getLikesByPostId(Long postId) {
        return postLikeRepository.searchByPostId(postId);
    }

    public Optional<PostLike> getLikeByPostIdAndUserId(Long postId, Long userId) {
        return postLikeRepository.searchByPostIdAndUserId(postId, userId);
    }

    public boolean userHasLikedPost(Long postId, Long userId) {
        return postLikeRepository.existsByPostIdAndUserId(postId, userId);
    }

    public long numberOfLikesForPost(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }
}
