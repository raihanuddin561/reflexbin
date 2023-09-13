package com.reflexbin.reflexbin_api.dto;

import com.reflexbin.reflexbin_api.constant.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * BaseResponse class as main wrapper class
 */
@Builder
@Data
@AllArgsConstructor
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private ResponseType responseType;

    private Collection<String> message;

    private Object result;

    private Object error;

    private String code;
}