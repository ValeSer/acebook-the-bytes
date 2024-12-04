package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comments WHERE post_id= :postId",nativeQuery = true)
    List<Comment> searchByPostId(@Param("postId") Long postId);

    @Query(value = """
        SELECT
            comments.*,
            users.first_name,
            users.last_name,
            users.profile_photo_url
        FROM comments
        JOIN users ON users.id = comments.commenter_id
        WHERE comments.post_id = :postId
    """, nativeQuery = true)
    List<Object[]> searchAllDetailsByPostId(@Param("postId") Long postId);

    long countByPostId(Long postId);
}
