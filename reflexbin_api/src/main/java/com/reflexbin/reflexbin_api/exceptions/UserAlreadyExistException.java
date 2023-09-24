package com.reflexbin.reflexbin_api.exceptions;

/**
 * UserAlreadyExistException is a Runtime exception class
 */
public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message){
        super(message);
    }
}
