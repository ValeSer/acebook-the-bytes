package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentsService {
    @Autowired
    CommentRepository commentRepository;
    public Iterable<Comment> getCommentsByPostId(Long query) {
        return commentRepository.searchByPostId(query);
    }

    public List<Map<String, Object>> getCommentsDetailsByPostId(Long postId) {
        List<Object[]> commentResults = commentRepository.searchAllDetailsByPostId(postId);

        List<Map<String, Object>> commentsWithUserDetails = new ArrayList<>();
        for (Object[] row : commentResults) {
            Map<String, Object> commentData = new HashMap<>();
            commentData.put("id", row[0]);
            commentData.put("postId", row[1]);
            commentData.put("content", row[2]);
            commentData.put("commenterId", row[3]);
            commentData.put("createdAt", row[4]);
            commentData.put("firstName", row[5]);
            commentData.put("lastName", row[6]);
            commentData.put("profilePhotoUrl", row[7]);

            commentsWithUserDetails.add(commentData);
        }
        return commentsWithUserDetails;
    }
}
