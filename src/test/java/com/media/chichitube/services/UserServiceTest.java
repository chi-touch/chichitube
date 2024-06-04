package com.media.chichitube.services;

import com.media.chichitube.dtos.requests.CreateUserRequest;
import com.media.chichitube.dtos.requests.LoginRequest;
import com.media.chichitube.dtos.requests.UpdateMediaRequest;
import com.media.chichitube.dtos.responses.CreateUserResponse;
import com.media.chichitube.dtos.responses.LoginResponse;

import com.media.chichitube.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testToRegisterUser(){
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("chi@gmail.com");
        request.setPassword("password");


        CreateUserResponse response = userService.register(request);

        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));
    }

    @Test
    public void testThatUserCanLogin(){
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("chi@gmail.com");
        request.setPassword("password");
        userService.register(request);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("chi@gmail.com");
        loginRequest.setPassword("password");

        LoginResponse response = userService.login(loginRequest);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));
    }

    @Test
    @DisplayName("test that user can be retrieved by id")
    @Sql(scripts = {"/db/data.sql"})
    public void testGetUserById(){
        User user = userService.getById(200L);
        assertThat(user).isNotNull();
    }


    @Test
    public void testUpdateMediaFileCategory(){

        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("chi@gmail.com");
        request.setPassword("password");
        userService.register(request);

        UpdateMediaRequest mediaRequest = new UpdateMediaRequest();
//        mediaRequest.
    }


}
