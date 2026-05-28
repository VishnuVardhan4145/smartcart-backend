package com.smartcart.service;

import com.smartcart.entity.User;
import com.smartcart.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    @Test
    void shouldReturnAllUsers() {

        when(repository.findAll())
                .thenReturn(List.of(
                        new User(),
                        new User()));

        assertEquals(
                2,
                service.getAllUsers().size());
    }

    @Test
    void shouldRegisterUser() {

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("user123");

        when(passwordEncoder.encode(anyString()))
                .thenReturn("encodedPassword");

        when(repository.save(any(User.class)))
                .thenReturn(user);

        User saved = service.registerUser(user);

        assertEquals(
                "test@gmail.com",
                saved.getEmail());

        verify(repository, times(1))
                .save(any(User.class));
    }
}