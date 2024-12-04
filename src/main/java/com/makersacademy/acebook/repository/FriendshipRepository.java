package com.makersacademy.acebook.repository;
import com.makersacademy.acebook.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Friendship findBySenderIdAndReceiverId(Long userId, Long receiverId);
//    Iterable<Friendship> findByReceiverIdAndStatus(Long userId, String pending);
Iterable<Friendship> findByReceiverIdAndStatusAndSenderIdNot(Long receiverId, String status, Long senderId);

}
