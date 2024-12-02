package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    @Autowired
    CommentRepository commentRepository;
    public Iterable<Comment> getCommentsByPostId(Long query) {
        return commentRepository.searchByPostId(query);
    }
}
