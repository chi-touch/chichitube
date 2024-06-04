package com.media.chichitube.services;

import com.media.chichitube.dtos.requests.CreateUserRequest;
import com.media.chichitube.dtos.requests.LoginRequest;
import com.media.chichitube.dtos.responses.CreateUserResponse;
import com.media.chichitube.dtos.responses.LoginResponse;
import com.media.chichitube.exceptions.EmailAlreadyExistException;
import com.media.chichitube.exceptions.UserNotFoundException;
import com.media.chichitube.models.User;
import com.media.chichitube.respositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaverickHubUserService implements UserService {

    private final ModelMapper modelMapper;


    private final UserRepository userRepository;

    @Autowired
    public MaverickHubUserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }



    @Override
    public CreateUserResponse register(CreateUserRequest request) {

        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(request,User.class);
        User savedUser = userRepository.save(user);
       var response = modelMapper.map(savedUser,CreateUserResponse.class);
       response.setMessage("user registered successfully");
       return response;
    }

    private boolean EmailAlreadyExists(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(loginRequest, User.class);
        User savedUser = userRepository.save(user);
        var response = modelMapper.map(savedUser,LoginResponse.class);
        response.setMessage("login successfully");
        return response;
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->  new UserNotFoundException(
                        String.format("user with id %d not found", id)

                ));
    }


}
