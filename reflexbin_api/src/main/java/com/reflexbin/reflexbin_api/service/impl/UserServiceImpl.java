package com.reflexbin.reflexbin_api.service.impl;

import com.reflexbin.reflexbin_api.model.UserEntity;
import com.reflexbin.reflexbin_api.repository.UserRepository;
import com.reflexbin.reflexbin_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Userservice class
 *
 * @author raihan
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    /**
     * loadUserByUsername method
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails
     * @throws UsernameNotFoundException can be thrown
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );
        return new User(user.getEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
    }
}
