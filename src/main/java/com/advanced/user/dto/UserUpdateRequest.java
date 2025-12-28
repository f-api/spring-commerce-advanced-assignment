package com.advanced.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotBlank
    private String name;
    @Size(min = 8, max = 20)
    private String password;
}
