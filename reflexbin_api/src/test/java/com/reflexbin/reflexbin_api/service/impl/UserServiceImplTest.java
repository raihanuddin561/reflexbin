package com.reflexbin.reflexbin_api.service.impl;

import com.reflexbin.reflexbin_api.constant.ApplicationConstants;
import com.reflexbin.reflexbin_api.constant.enums.ResponseType;
import com.reflexbin.reflexbin_api.dto.BaseResponse;
import com.reflexbin.reflexbin_api.dto.request.UserRequestModel;
import com.reflexbin.reflexbin_api.exceptions.UserAlreadyExistException;
import com.reflexbin.reflexbin_api.model.RoleEntity;
import com.reflexbin.reflexbin_api.model.UserEntity;
import com.reflexbin.reflexbin_api.repository.RoleRepository;
import com.reflexbin.reflexbin_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("check valid user exist")
    public void whenValidUsername_thenUserShouldFound() {
        UserEntity userEntity = UserEntity.builder()
                .email("raihan@gmail.com").build();
        when(userRepository.findByEmail("raihan@gmail.com"))
                .thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        String email = "raihan@gmail.com";
        UserEntity found = userServiceImpl.getUserByEmail(email);
        assertEquals(email, found.getEmail());
    }

    @Test
    @DisplayName("check invalid user not exist")
    public void whenInValidUsername_thenUserShouldNotFound() {
        String email = "raihan1@gmail.com";
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userServiceImpl.getUserByEmail(email);
        });
        assertEquals(ApplicationConstants.USER_NOT_REGISTERED, exception.getLocalizedMessage());
    }

    @Test
    @DisplayName("check creating user with existing email")
    public void whenCreateUserWithExistingEmail_thenUserWillNotCreate() {
        String email = "raihan@gmail.com";
        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .build();
        UserRequestModel requestModel = UserRequestModel.builder()
                .email(email)
                .build();
        when(userRepository.findByEmail("raihan@gmail.com")).thenReturn(Optional.of(userEntity));
        UserAlreadyExistException exception =
                assertThrows(UserAlreadyExistException.class, () -> userServiceImpl.createUser(requestModel));
        assertEquals(ApplicationConstants.USER_ALREADY_EXIST, exception.getLocalizedMessage());
    }

    @Test
    @DisplayName("Test create user with valid data")
    public void whenCreateUserWithValidData() {
        String email = "raihan@gmail.com";
        UserRequestModel requestModel = UserRequestModel.builder()
                .email(email)
                .firstName("Raihan")
                .lastName("Uddin")
                .password("raihan")
                .build();
        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .active(true)
                .password("r")
                .firstName(requestModel.getFirstName())
                .lastName(requestModel.getLastName())
                .build();
        when(passwordEncoder.encode(anyString())).thenReturn("a").thenReturn("b").thenReturn("c");
        RoleEntity role = mock(RoleEntity.class);
        when(roleRepository.findByRole(anyString())).thenReturn(Optional.of(role));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        BaseResponse baseUserResponse = userServiceImpl.createUser(requestModel).getBody();
        assertNotNull(baseUserResponse);
        assertEquals(String.valueOf(HttpStatus.CREATED), baseUserResponse.getCode());
        assertNotNull(baseUserResponse.getResult());
        assertEquals(ResponseType.SUCCESS, baseUserResponse.getResponseType());
    }


}