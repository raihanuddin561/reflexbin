package com.reflexbin.reflexbin_api.controller;

import com.reflexbin.reflexbin_api.constant.APIEndpoints;
import com.reflexbin.reflexbin_api.constant.enums.ResponseType;
import com.reflexbin.reflexbin_api.dto.BaseResponse;
import com.reflexbin.reflexbin_api.dto.request.UserRequestModel;
import com.reflexbin.reflexbin_api.dto.response.UserResponseModel;
import com.reflexbin.reflexbin_api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
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
    @MockBean
    private UserServiceImpl userServiceImpl;
    private BaseResponse baseResponse;
    private UserResponseModel userResponseModel;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
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
        Object nullRes = null;
        mockMvc.perform(post(APIEndpoints.USER + APIEndpoints.USER_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(USER_REQUEST_BODY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseType").value(ResponseType.SUCCESS.name()))
                .andExpect(jsonPath("$.result.email").value(userResponseModel.getEmail()))
                .andExpect(jsonPath("$.error").value(nullRes));
    }

}