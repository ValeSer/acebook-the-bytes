package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.CommentLike;
import com.makersacademy.acebook.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
