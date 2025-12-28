package com.advanced.user.controller;

import com.advanced.common.consts.LoginUser;
import com.advanced.user.dto.*;
import com.advanced.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<UserRegisterResponse> register(
            @Valid @RequestBody UserRegisterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponse> login(
            @Valid @RequestBody UserLoginRequest request,
            HttpSession httpSession
    ) {
        SessionUser sessionUser = userService.login(request);
        httpSession.setAttribute(LoginUser.LOGIN_USER, sessionUser);

        return ResponseEntity.status(HttpStatus.OK).body(new UserLoginResponse(sessionUser.getId()));
    }

    @PostMapping("/users/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = LoginUser.LOGIN_USER, required = false) SessionUser sessionUser,
            HttpSession httpSession
    ) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }

        httpSession.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserGetResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserGetResponse> getOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    @PutMapping("/users")
    public ResponseEntity<UserUpdateResponse> update(
            @SessionAttribute(name = LoginUser.LOGIN_USER, required = false) SessionUser sessionUser,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUser, request));
    }

    @DeleteMapping("/users")
    public void delete(
            @SessionAttribute(name = LoginUser.LOGIN_USER, required = false) SessionUser sessionUser,
            @Valid @RequestBody UserDeleteRequest request
    ) {
        userService.delete(sessionUser, request);
    }
}
