package com.advanced.schedule.service;

import com.advanced.schedule.dto.*;
import com.advanced.schedule.entity.Schedule;
import com.advanced.schedule.repository.ScheduleRepository;
import com.advanced.user.dto.SessionUser;
import com.advanced.user.entity.User;
import com.advanced.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleCreateResponse save(SessionUser sessionUser, ScheduleCreateRequest request) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleCreateResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<ScheduleGetResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);
        return schedules
                .map(schedule -> new ScheduleGetResponse(
                        schedule.getId(),
                        schedule.getUser().getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ));
    }

    @Transactional(readOnly = true)
    public ScheduleGetResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("해당 일정이 없습니다.")
        );
        return new ScheduleGetResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public ScheduleUpdateResponse update(Long scheduleId, SessionUser sessionUser, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("해당 일정이 없습니다.")
        );
        // 작성 유저가 아니라면
        if (!ObjectUtils.nullSafeEquals(sessionUser.getId(), schedule.getUser().getId())) {
            throw new IllegalStateException("작성 유저가 아닙니다.");
        }
        schedule.update(
                request.getTitle(),
                request.getContent()
        );
        return new ScheduleUpdateResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long scheduleId, SessionUser sessionUser) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("해당 일정이 없습니다.")
        );
        // 작성 유저가 아닐 때
        if (!ObjectUtils.nullSafeEquals(sessionUser.getId(), schedule.getUser().getId())) {
            throw new IllegalStateException("작성 유저가 아닙니다.");
        }
        // 작성 유저일 때
        scheduleRepository.deleteById(scheduleId);
    }
}
