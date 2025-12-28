package com.advanced.comment.controller;

import com.advanced.comment.dto.CommentCreateRequest;
import com.advanced.comment.dto.CommentCreateResponse;
import com.advanced.comment.dto.CommentGetResponse;
import com.advanced.comment.service.CommentService;
import com.advanced.common.consts.LoginUser;
import com.advanced.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentCreateResponse> create(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = LoginUser.LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, sessionUser, request));
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<Page<CommentGetResponse>> getAll(
            @PathVariable Long scheduleId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(scheduleId, page, size));
    }
}
