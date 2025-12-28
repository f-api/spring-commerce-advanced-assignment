package com.advanced.schedule.error;

import com.advanced.common.error.ServiceException;
import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ServiceException {
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message); // HttpStatus.NOT_FOUND 지정
    }
}
