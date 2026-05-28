package com.smartcart.controller;

import com.smartcart.dto.LoginRequest;
import com.smartcart.dto.LoginResponse;
import com.smartcart.dto.RefreshRequest;
import com.smartcart.entity.User;
import com.smartcart.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.registerUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/login")
public LoginResponse login(
        @RequestBody LoginRequest request) {

    return service.login(request);
}

@PostMapping("/refresh")
public LoginResponse refresh(
        @RequestBody RefreshRequest request) {

    return service.refreshToken(
            request.getRefreshToken());
}

@PostMapping("/logout")
public String logout(
        @RequestBody RefreshRequest request) {

    service.logout(
            request.getRefreshToken());

    return "Logged Out";
}
}