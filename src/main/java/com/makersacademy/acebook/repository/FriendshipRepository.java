package com.makersacademy.acebook.repository;
import com.makersacademy.acebook.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Friendship findBySenderIdAndReceiverId(Long userId, Long receiverId);

    Iterable<Friendship> findByReceiverIdAndStatus(Long userId, String pending);

    @Query(value = "SELECT COUNT(*) FROM friendships WHERE sender_id = :userId OR receiver_id = :userId", nativeQuery = true)
    long countFriendshipsForUser(long userId);

    @Query(value = "SELECT * FROM friendships WHERE sender_id = :userId OR receiver_id = :userId", nativeQuery = true)
    List<Friendship> findFriendshipsForUser(long userId);

//    Iterable<Friendship> findByReceiverIdAndStatus(Long userId, String pending);
    Iterable<Friendship> findByReceiverIdAndStatusAndSenderIdNot(Long receiverId, String status, Long senderId);

}
