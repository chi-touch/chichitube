package com.media.chichitube.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.media.chichitube.dtos.requests.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;

@Component
@AllArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //When u want to implemtent a method that returns one thing use extends OncePerRequestFilter


    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //1. Retrieve authentication credentials fro the request body
           InputStream requestBodyStream = request.getInputStream();
           //2. Convert the json data from 1 t java object(LoginRequest)
            LoginRequest loginRequest  =
                    mapper.readValue(requestBodyStream, LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            //3. Create an authentication object that is not yet authenticated
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(username, password);
            //4. Pass the unauthenticated Authentication object to the AuthenticationManager for authentication
            //4b. Get the AUthentication result from the AuthenticationManager.
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            //5. Put the authentication result in the security context
            SecurityContextHolder. getContext().setAuthentication(authenticationResult);
            return authenticationResult;

       }catch (IOException exception){
            throw new BadCredentialsException(exception..getMessage());

        }


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

    }
}
