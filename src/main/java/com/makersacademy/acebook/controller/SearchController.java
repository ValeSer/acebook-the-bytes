package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.service.UserService;
import com.makersacademy.acebook.repository.FriendshipRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendshipRepository friendshipRepository;

//    @GetMapping("/search")
//    public ModelAndView viewSearchResults(@RequestParam(value = "nav-search", required = false) String query) {
//        ModelAndView searchView = new ModelAndView("/search/index");
//
//        String username = userService.getAuthenticatedUserEmail();
//        User userDetails = userRepository.findUserByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        Long userId = userDetails.getId();
//
//        Iterable<User> allUsers = userService.getUsersBySearchTerm(query);
//
//        List<User> filteredUsers = new ArrayList<>();
//
//        for (User user : allUsers) {
//            if (!user.getId().equals(userId)) {
//                filteredUsers.add(user);
//            }
//        }
//
//        List<Boolean> friendshipsExist = new ArrayList<>();
//
//        for (User user : filteredUsers) {
//            Friendship friendship = friendshipRepository.findBySenderIdAndReceiverId(userId, user.getId());
//            if (friendship == null) {
//                friendship = friendshipRepository.findBySenderIdAndReceiverId(user.getId(), userId);
//            }
//            friendshipsExist.add(friendship != null);
//        }
//
//        searchView.addObject("users", filteredUsers);
//        searchView.addObject("friendshipsExist", friendshipsExist);
//
//        return searchView;
//    }


    @GetMapping("/search")
    public ModelAndView viewSearchResults(@RequestParam(value = "nav-search", required = false) String query) {
        // Creazione della vista
        ModelAndView searchView = new ModelAndView("/search/index");
        User currentUser = userService.getUserProfile();

        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long currentUserId = userDetails.getId();

        Iterable<User> allUsers = userService.getUsersBySearchTerm(query);
        Map<Long, Integer> mutualFriendsCountMap = new HashMap<>();

        List<User> filteredUsers = new ArrayList<>();
        for (User user : allUsers) {
            if (!user.getId().equals(currentUserId)) {
                filteredUsers.add(user);
            }
        }

        Map<Long, String> friendshipStatuses = new HashMap<>();
        boolean friendshipsExist = false;

        for (User user : filteredUsers) {
            int mutualFriendsCount = friendshipRepository.countMutualFriends(currentUserId, user.getId());
            mutualFriendsCountMap.put(user.getId(), mutualFriendsCount);

            Friendship friendship = friendshipRepository.findBySenderIdAndReceiverId(currentUserId, user.getId());
            if (friendship == null) {
                friendship = friendshipRepository.findBySenderIdAndReceiverId(user.getId(), currentUserId);
            }

            if (friendship != null) {
                friendshipStatuses.put(user.getId(), friendship.getStatus());
                friendshipsExist = true;
            } else {
                friendshipStatuses.put(user.getId(), "none");
            }
        }


//        searchView.addObject("users", allUsers);
        searchView.addObject("friendshipsExist", friendshipsExist);
        searchView.addObject("currentUserId", currentUserId);
        searchView.addObject("users", filteredUsers);
        searchView.addObject("friendshipStatuses", friendshipStatuses);
        searchView.addObject("currentUser", currentUser);
        searchView.addObject("post", new Post());
        searchView.addObject("mutualFriendsCount", mutualFriendsCountMap);
        //        searchView.addObject("friendshipsExist", friendshipsExist);
        return searchView;
    }

}

