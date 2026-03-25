package com.discpline.discipline_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.discpline.discipline_tracker.entity.User;
import com.discpline.discipline_tracker.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/{id}")
public User getUserById(@PathVariable Long id){
    return userRepository.findById(id).orElseThrow();
}

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}