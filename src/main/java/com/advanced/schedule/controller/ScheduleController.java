package com.advanced.schedule.controller;

import com.advanced.common.consts.LoginUser;
import com.advanced.schedule.dto.*;
import com.advanced.schedule.service.ScheduleService;
import com.advanced.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleCreateResponse> create(
            @SessionAttribute(name = LoginUser.LOGIN_USER, required = false) SessionUser sessionUser,
            @Valid @RequestBody ScheduleCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(sessionUser, request));
    }

    @GetMapping("/schedules")
    public ResponseEntity<Page<ScheduleGetResponse>> getAll(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(page, size));
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleGetResponse> getOne(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> update(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = LoginUser.LOGIN_USER, required = false) SessionUser sessionUser,
            @Valid @RequestBody ScheduleUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, sessionUser, request));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public void delete(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = LoginUser.LOGIN_USER, required = false) SessionUser sessionUser
    ) {
        scheduleService.delete(scheduleId, sessionUser);
    }
}
