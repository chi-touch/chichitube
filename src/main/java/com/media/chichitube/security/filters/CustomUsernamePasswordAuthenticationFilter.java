package com.media.chichitube.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.media.chichitube.dtos.requests.LoginRequest;
import com.media.chichitube.dtos.responses.BaseResponses;
import com.media.chichitube.dtos.responses.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //When u want to implemtent a method that returns one thing use extends OncePerRequestFilter


    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

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
            throw new BadCredentialsException(exception.getMessage());

        }







    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
      LoginResponse loginResponse = new LoginResponse();
      loginResponse.setToken(generateAccessToken(authResult));
      loginResponse.setMessage("Succesful Authentication");
      BaseResponses<LoginResponse> authResponses = new BaseResponses<>();
      authResponses.setCode(HttpStatus.OK.value());
      authResponses.setData(loginResponse);
      authResponses.setStatus(true);

        response.getOutputStream().write(mapper.writeValueAsBytes(authResponses));
        response.flushBuffer();
        chain.doFilter(request,response);
    }

    private static String generateAccessToken(Authentication authResult) {
       return JWT.create()
                  .withIssuer("chichi_tube")
                  .withArrayClaim("roles",
                          getClaimsFrom(authResult.getAuthorities()))
                  .withExpiresAt(Instant.now().plusSeconds(24 * 60 * 60))
                  .sign(Algorithm.HMAC512("secret"));

    }

    private static String[] getClaimsFrom(Collection<? extends GrantedAuthority> authorities){
        return authorities.stream()
                .map((grantedAuthority)-> grantedAuthority.getAuthority())
                .toArray(String[]:: new);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException exception) throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(exception.getMessage());
        BaseResponses<LoginResponse>baseResponses = new BaseResponses<>();
        baseResponses.setData(loginResponse);
        baseResponses.setStatus(false);
        baseResponses.setCode(HttpStatus.UNAUTHORIZED.value());

        response.getOutputStream().
                write(mapper.writeValueAsBytes(baseResponses));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.flushBuffer();


    }



}
