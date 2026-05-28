package com.smartcart.controller;

import com.smartcart.dto.LoginResponse;
import com.smartcart.entity.User;
import com.smartcart.service.UserService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void shouldLoadUsersEndpoint() throws Exception {

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllUsers() throws Exception {

        when(service.getAllUsers())
                .thenReturn(List.of(
                        new User()));

        mockMvc.perform(
                        get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
void shouldLogin() throws Exception {

    LoginResponse response =
            new LoginResponse(
                    "access",
                    "refresh");

    when(service.login(any()))
            .thenReturn(response);

    mockMvc.perform(
            post("/api/users/login")
                    .contentType(
                            MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "email":"admin@gmail.com",
                                "password":"admin123"
                            }
                            """))
            .andExpect(status().isOk());
}
}