package com.discpline.discipline_tracker.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.discpline.discipline_tracker.entity.User;
import com.discpline.discipline_tracker.repository.UserRepository;

@RestController
@CrossOrigin(origins = "https://discipline-frontend-zeta.vercel.app")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        Optional<User> existing = userRepository.findByEmail(user.getEmail());

        if(existing.isPresent()){
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User loginUser){

        Optional<User> user = userRepository.findByEmail(loginUser.getEmail());

        if(user.isPresent() && user.get().getPassword().equals(loginUser.getPassword())){
            return user.get();
        }

        throw new RuntimeException("Invalid credentials");
    }
}