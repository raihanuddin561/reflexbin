package com.reflexbin.reflexbin_api.service.impl;

import com.reflexbin.reflexbin_api.constant.ApplicationConstants;
import com.reflexbin.reflexbin_api.dto.request.UserRequestModel;
import com.reflexbin.reflexbin_api.dto.response.UserResponseModel;
import com.reflexbin.reflexbin_api.exceptions.UserAlreadyExistException;
import com.reflexbin.reflexbin_api.model.RoleEntity;
import com.reflexbin.reflexbin_api.model.UserEntity;
import com.reflexbin.reflexbin_api.model.enums.Role;
import com.reflexbin.reflexbin_api.repository.RoleRepository;
import com.reflexbin.reflexbin_api.repository.UserRepository;
import com.reflexbin.reflexbin_api.service.UserService;
import com.reflexbin.reflexbin_api.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Userservice class
 *
 * @author raihan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * loadUserByUsername method
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails
     * @throws UsernameNotFoundException can be thrown
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        UserEntity user = getUserByEmail(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (RoleEntity role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new User(user.getEmail(), user.getPassword(),
                user.isActive(), true, true,
                true, grantedAuthorities);
    }

    /**
     * get user by email
     *
     * @param username pass email
     * @return UserEntity
     */
    @Override
    public UserEntity getUserByEmail(String username) {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not registered!")
        );
    }

    /**
     * create user with user information
     *
     * @param userRequestModel user information model
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<UserResponseModel> createUser(UserRequestModel userRequestModel) {
        log.info("creating user...");
        Optional<UserEntity> userEntity = userRepository.findByEmail(userRequestModel.getEmail());
        if (userEntity.isPresent()) throw new UserAlreadyExistException(ApplicationConstants.USER_ALREADY_EXIST);
        ModelMapper modelMapper = new ModelMapper();
        UserEntity user = modelMapper.map(userRequestModel, UserEntity.class);
        log.info("Preparing role for user {}", userRequestModel.getEmail());
        RoleEntity userRole = RoleEntity.builder()
                .role(Role.ROLE_USER.name())
                .build();
        RoleEntity userEntityRole = userRoleRepository.findByRole(Role.ROLE_USER.name()).orElse(
                userRoleRepository.save(userRole)
        );
        log.info("User role prepared for user: {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestModel.getPassword()));
        user.setRoles(List.of(userEntityRole));
        user.setCreatedAt(DateUtils.getCurrentDateTime());
        user.setActive(true);
        user.setLastUpdatedAt(DateUtils.getCurrentDateTime());
        UserEntity savedUser = userRepository.save(user);
        log.info("Successfully user created for {}", user.getEmail());
        UserResponseModel userResponseModel = modelMapper.map(savedUser, UserResponseModel.class);
        return new ResponseEntity<>(userResponseModel, HttpStatus.CREATED);
    }
}
