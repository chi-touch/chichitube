package com.media.chichitube.security.filters;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.media.chichitube.security.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.media.chichitube.security.utils.SecurityUtils.JWT_PREFIX;
import static com.media.chichitube.security.utils.SecurityUtils.PUBLIC_ENDPOINTS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /**
         * 1a. Retrieve request path
             * 1b. If request path from 1a is a public path,
             *    call the next filter in chain and terminate this filters execution
         * 2. Retrieve access token from the request header
         * 3. Decode access token
         * 4. Extract token permission
         * 5. add token permission to security context
         * 6. call the next filter in the next filterchain
         */

        String requestPath  = request.getServletPath();
        boolean isRequestPathPublic = PUBLIC_ENDPOINTS.contains(requestPath);
        if(isRequestPathPublic) filterChain.doFilter(request, response);
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null) {
            String token = authorizationHeader.substring(JWT_PREFIX.length()).strip();
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret".getBytes()))
                    .withIssuer("chichi_tube")
                    .withClaimPresence("roles")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles")
                    .asList(SimpleGrantedAuthority.class);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);




    }
}
