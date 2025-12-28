package com.advanced.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterRequest {

    @NotBlank
    private String name;
    @Email
    private String email;
    @Size(min = 8, max = 20, message = "비밀번호는 8글자에서 20글자 사이여야 합니다.")
    private String password;
}
