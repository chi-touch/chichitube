package com.media.chichitube.services;

import com.media.chichitube.dtos.requests.CreateUserRequest;
import com.media.chichitube.dtos.requests.LoginRequest;
import com.media.chichitube.dtos.responses.CreateUserResponse;
import com.media.chichitube.dtos.responses.LoginResponse;
import com.media.chichitube.exceptions.EmailAlreadyExistException;
import com.media.chichitube.exceptions.UserNotFoundException;
import com.media.chichitube.models.Authority;
import com.media.chichitube.models.User;
import com.media.chichitube.respositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service

public class MaverickHubUserService implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public MaverickHubUserService(ModelMapper modelMapper,
                                  UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public CreateUserResponse register(CreateUserRequest request) {

        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(request,User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(new HashSet<>());
        var authorities = user.getAuthorities();
        authorities.add(Authority.USER);
        user.setAuthorities(authorities);
        User savedUser = userRepository.save(user);
       var response = modelMapper.map(savedUser,CreateUserResponse.class);
       response.setMessage("user registered successfully");
       return response;
    }



//    @Override
//    public LoginResponse login(LoginRequest loginRequest) {
//        ModelMapper modelMapper = new ModelMapper();
//        User user = modelMapper.map(loginRequest, User.class);
//        User savedUser = userRepository.save(user);
//        var response = modelMapper.map(savedUser,LoginResponse.class);
//        response.setMessage("login successfully");
//        return response;
//    }

    @Override
    public User getById(long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() ->  new UserNotFoundException(
                        String.format("user with id %d not found", id)

                ));
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new UserNotFoundException("user not found"));
        return user;
    }


}
