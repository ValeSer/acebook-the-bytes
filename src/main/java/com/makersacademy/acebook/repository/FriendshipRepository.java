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


    @Query(value = """
        SELECT COUNT(*)
        FROM (
            SELECT CASE
                     WHEN f.sender_id = :loggedInUserId THEN f.receiver_id
                     WHEN f.receiver_id = :loggedInUserId THEN f.sender_id
                 END AS friend_id
            FROM friendships f
            WHERE :loggedInUserId IN (f.sender_id, f.receiver_id)
        ) AS logged_in_user_friends
        INNER JOIN (
            SELECT CASE
                     WHEN f.sender_id = :searchCardUserId THEN f.receiver_id
                     WHEN f.receiver_id = :searchCardUserId THEN f.sender_id
                 END AS friend_id
            FROM friendships f
            WHERE :searchCardUserId IN (f.sender_id, f.receiver_id)
        ) AS search_user_friends
        ON logged_in_user_friends.friend_id = search_user_friends.friend_id
    """, nativeQuery = true)
    int countMutualFriends(Long loggedInUserId, Long searchCardUserId);
}
