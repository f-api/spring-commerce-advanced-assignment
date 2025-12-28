package com.advanced.common.advice;

import com.advanced.common.error.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🎯 커스텀 비즈니스 예외 처리
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body("요청 오류: " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst() // 첫 번째 에러를 Optional로 가져옴
                .map(fieldError -> fieldError.getDefaultMessage()) // 있다면 메시지로 변환
                .orElse("입력 값이 올바르지 않습니다."); // 없다면 기본 메시지 사용

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
