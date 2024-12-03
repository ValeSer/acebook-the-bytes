package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Friendship;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserService;
import com.makersacademy.acebook.repository.FriendshipRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/search")
    public ModelAndView viewSearchResults(@RequestParam(value = "nav-search", required = false) String query) {
        ModelAndView searchView = new ModelAndView("/search/index");

        String username = userService.getAuthenticatedUserEmail();
        User userDetails = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = userDetails.getId();

        Iterable<User> allUsers = userService.getUsersBySearchTerm(query);

        List<Boolean> friendshipsExist = new ArrayList<>();

        for (User user : allUsers) {
            Friendship friendship = friendshipRepository.findBySenderIdAndReceiverId(userId, user.getId());
            if (friendship == null) {
                friendship = friendshipRepository.findBySenderIdAndReceiverId(user.getId(), userId);
            }
            friendshipsExist.add(friendship != null);
        }

        searchView.addObject("users", allUsers);
        searchView.addObject("friendshipsExist", friendshipsExist);

        return searchView;
    }
}

