package com.reflexbin.reflexbin_api.service;

import com.reflexbin.reflexbin_api.dto.request.UserRequestModel;
import com.reflexbin.reflexbin_api.dto.response.UserResponseModel;
import com.reflexbin.reflexbin_api.model.UserEntity;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<UserResponseModel> createUser(UserRequestModel userRequestModel);

    UserEntity getUserByEmail(String email);
}
