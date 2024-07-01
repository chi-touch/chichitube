package com.media.chichitube.security.filters;


import com.media.chichitube.security.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.media.chichitube.security.utils.SecurityUtils.PUBLIC_ENDPOINTS;

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
        request.getHeader("AUTHORIZATION");


    }
}
