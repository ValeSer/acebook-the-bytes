package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUser1IdOrUser2Id(Long userId, Long userId2);
    @Query("SELECT c FROM Chat c WHERE (c.user1Id = :user1Id1 AND c.user2Id = :user2Id1) OR (c.user1Id = :user2Id2 AND c.user2Id = :user1Id2)")
    Chat findByUser1IdAndUser2IdOrUser2IdAndUser1Id(@Param("user1Id1") Long user1Id1, @Param("user2Id1") Long user2Id1, @Param("user2Id2") Long user2Id2, @Param("user1Id2") Long user1Id2);


}
