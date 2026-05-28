package com.smartcart.service;

import com.smartcart.dto.LoginRequest;
import com.smartcart.dto.LoginResponse;
import com.smartcart.entity.RefreshToken;
import com.smartcart.entity.User;
import com.smartcart.repository.RefreshTokenRepository;
import com.smartcart.repository.UserRepository;
import com.smartcart.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenRepository refreshRepo;

    public User registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()));

        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public LoginResponse login(
        LoginRequest request) {

    User user =
            repository.findByEmail(
                    request.getEmail())
                    .orElseThrow();

    if(passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {

        String accessToken =
                JwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole());

        String refreshToken =
                JwtUtil.generateRefreshToken(
                        user.getEmail());

        RefreshToken token =
                new RefreshToken();

        token.setToken(refreshToken);

        token.setExpiryDate(
                LocalDateTime.now()
                        .plusDays(7));

        token.setUser(user);

        refreshRepo.save(token);

        return new LoginResponse(
                accessToken,
                refreshToken);
    }

    throw new RuntimeException(
            "Invalid Credentials");
}

    public User getUserByEmail(String email) {

        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));
    }

    public LoginResponse refreshToken(String refreshToken) {

        RefreshToken token =
                refreshRepo.findByToken(refreshToken)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid refresh token"));

        if(token.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Refresh token expired");
        }

        String email =
                token.getUser().getEmail();

        String accessToken =
                JwtUtil.generateToken(
                        email,
                        token.getUser().getRole());

        return new LoginResponse(
                accessToken,
                refreshToken);
    }

    public void logout(String refreshToken) {
        refreshRepo.deleteByToken(refreshToken);
    }

}