package com.media.chichitube.security.config;

import com.media.chichitube.security.filters.CustomAuthorizationFilter;
import com.media.chichitube.security.filters.CustomUsernamePasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;

    private final CustomAuthorizationFilter authorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        var authenticationFilter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/v1/auth");
        return http.csrf(c->c.disable())
                .cors(c->c.disable())
                .sessionManagement(c->c.sessionCreationPolicy(STATELESS))
                .addFilterAt(authenticationFilter,
                        BasicAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter,
                        CustomUsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c->
//                        c.requestMatchers("/api/v1/auth","/api/v1/media").permitAll())
                        c.requestMatchers("/api/v1/auth").permitAll()
                                .requestMatchers("/api/v1/media").hasAuthority("USER"))
                .build();
    }




}
