package com.reflexbin.reflexbin_api.exceptions;

import com.reflexbin.reflexbin_api.constant.enums.ResponseType;
import com.reflexbin.reflexbin_api.dto.BaseResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

import static com.reflexbin.reflexbin_api.constant.ApplicationConstants.MESSAGE;
import static com.reflexbin.reflexbin_api.constant.ApplicationConstants.STATUS_FAILED;

/**
 * Global Exception handler for this App
 *
 * @author raihan
 */
@ControllerAdvice
public class AppExceptionHandler {
    private static BaseResponse errorModel;

    /**
     * setErrorModel method
     *
     * @param status String
     * @param error  Map
     */
    public static void setErrorModel(String status, Map<Object, Object> error,String message) {
        errorModel = BaseResponse.builder()
                .code(status)
                .error(error)
                .message(List.of(message))
                .responseType(ResponseType.ERROR)
                .build();
    }
    public static void setErrorModel(String status, Map<Object, Object> error) {
        errorModel = BaseResponse.builder()
                .code(status)
                .error(error)
                .responseType(ResponseType.ERROR)
                .build();
    }
    /**
     * badCredentialsException
     *
     * @param exception BadCredentialsException
     * @return Error ResponseModel
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse> badCredentialsException(BadCredentialsException exception) {
        setErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), Map.of(
                MESSAGE, exception.getLocalizedMessage()),exception.getLocalizedMessage());
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    /**
     * usernameNotFoundException method
     *
     * @param exception UsernameNotFoundException
     * @return Error ResponseModel
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse> usernameNotFoundException(
            UsernameNotFoundException exception) {
        setErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), Map.of( MESSAGE,
                exception.getLocalizedMessage()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<BaseResponse> userAlreadyExistException(
            UserAlreadyExistException exception) {
        setErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), Map.of(MESSAGE,
                exception.getLocalizedMessage()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<BaseResponse> expiredJwtException(
            ExpiredJwtException exception) {
        setErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), Map.of( MESSAGE,
                exception.getLocalizedMessage()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> anyOtherException(
            Exception exception) {
        setErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), Map.of( MESSAGE,
                exception.getLocalizedMessage()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }
}
