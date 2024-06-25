package com.media.chichitube.services;

import com.media.chichitube.dtos.requests.CreateUserRequest;
import com.media.chichitube.dtos.requests.LoginRequest;
import com.media.chichitube.dtos.responses.CreateUserResponse;
import com.media.chichitube.dtos.responses.LoginResponse;
import com.media.chichitube.exceptions.UserNotFoundException;
import com.media.chichitube.models.User;

public interface UserService {
   CreateUserResponse register(CreateUserRequest request);

    User getById(long id) throws UserNotFoundException;

   User getUserByUsername(String username) throws UserNotFoundException;
}
