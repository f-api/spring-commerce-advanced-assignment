package com.advanced.user.error;

import com.advanced.common.error.ServiceException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message); // HttpStatus.NOT_FOUND 지정
    }
}
