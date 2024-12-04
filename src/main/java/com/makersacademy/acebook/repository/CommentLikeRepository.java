package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    @Query(value = "SELECT * FROM comment_likes WHERE comment_id = :commentId",nativeQuery = true)
    List<CommentLike> searchByCommentId(@Param("commentId") Long commentId);

    @Query(value = "SELECT * FROM comment_likes WHERE comment_id = :commentId AND user_id = :userId", nativeQuery = true)
    Optional<CommentLike> searchByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
}
