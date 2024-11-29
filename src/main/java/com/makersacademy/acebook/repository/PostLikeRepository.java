package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
