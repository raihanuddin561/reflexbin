package com.reflexbin.reflexbin_api.security;

import com.reflexbin.reflexbin_api.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security config class
 *
 * @author raihan
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTService jwtService;

    /**
     * authenticationManager bean
     *
     * @param authConfig AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception can be thrown
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * securityFilterChain bean
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain bean
     * @throws Exception for securityFilterChain bean
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().permitAll();
                })
                .addFilter(new CustomAuthFilter(authenticationManager, jwtService))
        ;
        return http.build();
    }
}
