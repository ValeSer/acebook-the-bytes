package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Query(value = "SELECT * FROM post_likes WHERE post_id = :postId",nativeQuery = true)
    List<PostLike> searchByPostId(@Param("postId") Long postId);

    @Query(value = "SELECT * FROM post_likes WHERE post_id = :postId AND user_id = :userId",nativeQuery = true)
    Optional<PostLike> searchByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    long countByPostId(Long postId);
}
