package com.reflexbin.reflexbin_api.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
@Data
@Builder
public class ErrorModel {
    private String status;
    private Map<Object,Object> error;
}
