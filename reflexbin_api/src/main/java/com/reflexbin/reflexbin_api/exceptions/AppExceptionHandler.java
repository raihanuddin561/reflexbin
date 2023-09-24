package com.reflexbin.reflexbin_api.exceptions;

import com.reflexbin.reflexbin_api.model.ErrorModel;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    private static ErrorModel errorModel;

    /**
     * setErrorModel method
     *
     * @param status String
     * @param error  Map
     */
    public static void setErrorModel(String status, Map<Object, Object> error) {
        errorModel = ErrorModel.builder()
                .status(status)
                .error(error)
                .build();
    }

    /**
     * badCredentialsException
     *
     * @param exception BadCredentialsException
     * @return Error ResponseModel
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorModel> badCredentialsException(BadCredentialsException exception) {
        setErrorModel(STATUS_FAILED, Map.of("code", HttpStatus.BAD_REQUEST.value(),
                MESSAGE, exception.getLocalizedMessage()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    /**
     * usernameNotFoundException method
     *
     * @param exception UsernameNotFoundException
     * @return Error ResponseModel
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorModel> usernameNotFoundException(
            UsernameNotFoundException exception) {
        setErrorModel(STATUS_FAILED, Map.of("code",
                HttpStatus.BAD_REQUEST.value(), MESSAGE,
                exception.getLocalizedMessage()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorModel> userAlreadyExistException(
            UserAlreadyExistException exception) {
        setErrorModel(STATUS_FAILED, Map.of("code",
                HttpStatus.BAD_REQUEST.value(), MESSAGE,
                exception.getLocalizedMessage()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorModel> expiredJwtException(
            ExpiredJwtException exception) {
        setErrorModel(STATUS_FAILED, Map.of("code",
                HttpStatus.BAD_REQUEST.value(), MESSAGE,
                exception.getLocalizedMessage()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> anyOtherException(
            Exception exception) {
        setErrorModel(STATUS_FAILED, Map.of("code",
                HttpStatus.BAD_REQUEST.value(), MESSAGE,
                exception.getLocalizedMessage()));
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }
}
