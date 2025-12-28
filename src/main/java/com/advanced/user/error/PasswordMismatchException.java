package com.advanced.user.error;

import com.advanced.common.error.ServiceException;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends ServiceException {
    public PasswordMismatchException(String message) {
        super(HttpStatus.BAD_REQUEST, message); // HttpStatus.NOT_FOUND 지정
    }
}
