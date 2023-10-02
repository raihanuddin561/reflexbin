package com.reflexbin.reflexbin_api.controller;

import com.reflexbin.reflexbin_api.constant.APIEndpoints;
import com.reflexbin.reflexbin_api.constant.ApplicationConstants;
import com.reflexbin.reflexbin_api.constant.enums.ResponseType;
import com.reflexbin.reflexbin_api.dto.BaseResponse;
import com.reflexbin.reflexbin_api.dto.request.UserRequestModel;
import com.reflexbin.reflexbin_api.dto.response.UserResponseModel;
import com.reflexbin.reflexbin_api.model.UserEntity;
import com.reflexbin.reflexbin_api.repository.UserRepository;
import com.reflexbin.reflexbin_api.security.CustomAuthFilter;
import com.reflexbin.reflexbin_api.service.JWTService;
import com.reflexbin.reflexbin_api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest()
@AutoConfigureMockMvc
class UserControllerTest {
    private final String USER_REQUEST_BODY = "{\n" +
            "    \"firstName\":\"raihan\",\n" +
            "    \"lastName\":\"uddin\",\n" +
            "    \"email\":\"raihan@gmail.com\",\n" +
            "    \"password\":\"raihan\"\n" +
            "}";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @MockBean
    private UserServiceImpl userServiceImpl;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    private BaseResponse baseResponse;
    private UserResponseModel userResponseModel;
    private UserEntity userEntity;
    private CustomAuthFilter customAuthFilter;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Test create user api with valid data")
    public void testCreateUserAPIWithValidData_ThenReturnSuccessResponse() throws Exception {

        userResponseModel = UserResponseModel.builder()
                .email("raihan@gmail.com")
                .firstName("raihan")
                .lastName("uddin")
                .build();
        baseResponse = BaseResponse.builder()
                .code(String.valueOf(HttpStatus.CREATED))
                .error(null)
                .responseType(ResponseType.SUCCESS)
                .result(userResponseModel)
                .build();
        when(userServiceImpl.createUser(any(UserRequestModel.class)))
                .thenReturn(new ResponseEntity<>(baseResponse, HttpStatus.CREATED));
        mockMvc.perform(post(APIEndpoints.USER + APIEndpoints.USER_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USER_REQUEST_BODY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseType").value(ResponseType.SUCCESS.name()))
                .andExpect(jsonPath("$.result.email").value(userResponseModel.getEmail()))
                .andExpect(jsonPath("$.error").value((Object) null));
    }

    @Test
    @DisplayName("Test login with valid user info")
    public void testLoginWithValidUser_thenReturnToken() throws Exception {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        userEntity = UserEntity.builder()
                .password("abc")
                .email("raihan@gmail.com")
                .lastName("uddin")
                .active(true)
                .build();
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userServiceImpl.getUserByEmail(anyString())).thenReturn(userEntity);
        when(userServiceImpl.loadUserByUsername(anyString())).thenReturn(
                new User(userEntity.getEmail(), userEntity.getPassword(), true,
                        true, true, true, authorities));
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"email\":\"raihan@gmail.com\",\n" +
                                "    \"password\":\"abc\"\n" +
                                "}")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.result.token").exists())
                .andExpect(jsonPath("$.responseType").value(ResponseType.SUCCESS.name()));
    }

    @Test
    @DisplayName("Test login with invalid user info")
    public void testLoginWithInvalidUser_thenReturnBadCredentialException() throws Exception {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        userEntity = UserEntity.builder()
                .password("abc")
                .email("raihan@gmail.com")
                .lastName("uddin")
                .active(true)
                .build();
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        when(userServiceImpl.getUserByEmail(anyString())).thenReturn(userEntity);
        when(userServiceImpl.loadUserByUsername(anyString())).thenReturn(
                new User(userEntity.getEmail(), userEntity.getPassword(), true,
                        true, true, true, authorities));
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"email\":\"raihan@gmail.com\",\n" +
                                "    \"password\":\"abc\"\n" +
                                "}")
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message")
                        .value(ApplicationConstants.BAD_CREDENTIALS))
                .andExpect(jsonPath("$.responseType").value(ResponseType.ERROR.name()))
                .andExpect(jsonPath("$.result").value((Object) null))
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.toString()));

    }

}