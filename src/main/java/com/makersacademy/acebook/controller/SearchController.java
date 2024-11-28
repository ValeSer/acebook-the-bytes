package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserService;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/search")
    public ModelAndView viewSearchResults(@RequestParam(value = "nav-search", required = false) String query) {
        ModelAndView searchView = new ModelAndView("/search/index");
        List<User> allUsers = (List<User>) userService.getUsersBySearchTerm(query);
        searchView.addObject("users", allUsers);
        return searchView;
    }
}
