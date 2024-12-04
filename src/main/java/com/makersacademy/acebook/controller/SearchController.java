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
        ModelAndView searchView = new ModelAndView("/search/index");

        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();

        Iterable<User> allUsers = userService.getUsersBySearchTerm(query);

        List<User> filteredUsers = new ArrayList<>();
        Map<Long, String> friendshipStatuses = new HashMap<>();

        for (User user : allUsers) {
            if (!user.getId().equals(userId)) {
                filteredUsers.add(user);
            }
        }

        for (User user : filteredUsers) {
            Friendship friendship = friendshipRepository.findBySenderIdAndReceiverId(userId, user.getId());
            if (friendship == null) {
                friendship = friendshipRepository.findBySenderIdAndReceiverId(user.getId(), userId);
            }

            if (friendship != null) {
                friendshipStatuses.put(user.getId(), friendship.getStatus());
            } else {
                friendshipStatuses.put(user.getId(), "none");
            }
        }


        searchView.addObject("users", filteredUsers);
        searchView.addObject("friendshipStatuses", friendshipStatuses);

        User user = userService.getUserProfile();
        searchView.addObject("currentUserId", user.getId());
        searchView.addObject("currentUser", user);
        searchView.addObject("post", new Post());
        searchView.addObject("users", allUsers);
        searchView.addObject("friendshipsExist", friendshipsExist);


        return searchView;
    }
}

