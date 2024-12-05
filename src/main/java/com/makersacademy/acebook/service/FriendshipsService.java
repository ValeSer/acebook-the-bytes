package com.makersacademy.acebook.service;

import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FriendshipRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FriendshipsService {
    @Autowired
    FriendshipRepository friendshipRepository;
    @Autowired
    UserRepository userRepository;

    public long numberOfFriendshipsForUser(Long userId) { return friendshipRepository.countFriendshipsForUser(userId); }

    public Map<Long, User> getFriendsForUser(Long userId) {
        List<Friendship> friendships = friendshipRepository.findFriendshipsForUser(userId);
        Map<Long, User> friendsMap = new HashMap<>();
        for (Friendship friendship : friendships) {
            Long friendId = (friendship.getSenderId().equals(userId)) ? friendship.getReceiverId() : friendship.getSenderId();
            Optional<User> friend = userRepository.findById(friendId);
            if (friend.isPresent()) {
                friendsMap.put(friendId, friend.get());
            }
        }
        return friendsMap;
    }
}
