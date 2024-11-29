package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    @Autowired
    CommentRepository commentRepository;
    public Iterable<Comment> getCommentsByPostId(Long query) {
        return commentRepository.searchByPostId(query);
    }
}
