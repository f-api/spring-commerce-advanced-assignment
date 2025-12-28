package com.advanced.comment.service;

import com.advanced.comment.dto.CommentCreateRequest;
import com.advanced.comment.dto.CommentCreateResponse;
import com.advanced.comment.dto.CommentGetResponse;
import com.advanced.comment.entity.Comment;
import com.advanced.comment.repository.CommentRepository;
import com.advanced.schedule.entity.Schedule;
import com.advanced.schedule.error.ScheduleNotFoundException;
import com.advanced.schedule.repository.ScheduleRepository;
import com.advanced.user.dto.SessionUser;
import com.advanced.user.entity.User;
import com.advanced.user.error.UserNotFoundException;
import com.advanced.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentCreateResponse save(Long scheduleId, SessionUser sessionUser, CommentCreateRequest request) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new UserNotFoundException("해당 유저가 없습니다.")
        );
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("해당 유저가 없습니다.")
        );
        Comment comment = new Comment(
                request.getContent(),
                user,
                schedule
        );
        Comment savedComment = commentRepository.save(comment);
        return new CommentCreateResponse(
                savedComment.getId(),
                savedComment.getContent()
        );
    }

    @Transactional(readOnly = true)
    public Page<CommentGetResponse> findAll(Long scheduleId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Comment> comments = commentRepository.findAllByScheduleId(scheduleId, pageable);
        return comments
                .map(comment -> new CommentGetResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getId(),
                        comment.getSchedule().getId()
                ));
    }
}
