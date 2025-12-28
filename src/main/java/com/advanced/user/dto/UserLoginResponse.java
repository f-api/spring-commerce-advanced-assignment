package com.advanced.user.dto;

import lombok.Getter;

@Getter
public class UserLoginResponse {

    private final Long userId;

    public UserLoginResponse(Long userId) {
        this.userId = userId;
    }
}
