package com.media.chichitube.security.servicees;

import com.media.chichitube.models.User;
import com.media.chichitube.security.models.SecureUser;
import com.media.chichitube.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
           User user = userService.getUserByUsername(username);
            return new SecureUser(user);
        }catch (UsernameNotFoundException exception){
            throw new UsernameNotFoundException(exception.getMessage());

        }

    }
}
