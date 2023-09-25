package com.reflexbin.reflexbin_api.controller;

import com.reflexbin.reflexbin_api.constant.APIEndpoints;
import com.reflexbin.reflexbin_api.dto.request.UserRequestModel;
import com.reflexbin.reflexbin_api.dto.response.UserResponseModel;
import com.reflexbin.reflexbin_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIEndpoints.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(APIEndpoints.USER_CREATE)
    public ResponseEntity<UserResponseModel> createUser(@RequestBody UserRequestModel userRequestModel) {
        return userService.createUser(userRequestModel);
    }
}
