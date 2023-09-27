package com.reflexbin.reflexbin_api.service;

import com.reflexbin.reflexbin_api.dto.BaseResponse;
import com.reflexbin.reflexbin_api.dto.request.UserRequestModel;
import com.reflexbin.reflexbin_api.model.UserEntity;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<BaseResponse> createUser(UserRequestModel userRequestModel);

    UserEntity getUserByEmail(String email);
}
