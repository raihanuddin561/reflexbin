package com.reflexbin.reflexbin_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
