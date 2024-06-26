package com.media.chichitube.security.manager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Class<? extends Authentication> authenticationType = authentication.getClass();
        if(authenticationProvider.supports(authenticationType))
          return authenticationProvider.authenticate(authentication);
        else throw new BadCredentialsException("Invalid credentials");

    }
}
