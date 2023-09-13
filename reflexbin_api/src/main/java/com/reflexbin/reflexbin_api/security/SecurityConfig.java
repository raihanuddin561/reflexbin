package com.reflexbin.reflexbin_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security config class
 * @author raihan
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * securityFilterChain bean
     * @param http HttpSecurity
     * @return SecurityFilterChain bean
     * @throws Exception for securityFilterChain bean
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.anyRequest().permitAll();
                });
        return http.build();
    }
}
