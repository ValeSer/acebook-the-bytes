package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.CommentLike;
import com.makersacademy.acebook.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikesService {
    @Autowired
    CommentLikeRepository commentLikeRepository;

    public Iterable<CommentLike> getLikesByCommentId(Long commentId) {
        return commentLikeRepository.searchByCommentId(commentId);
    }

    public Optional<CommentLike> getLikeByCommentIdAndUserId(Long commentId, Long userId) {
        return commentLikeRepository.searchByCommentIdAndUserId(commentId, userId);
    }

    public boolean userHasLikedComment(Long commentId, Long userId) {
        return commentLikeRepository.existsByCommentIdAndUserId(commentId, userId);
    }
}
