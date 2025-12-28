package com.advanced.comment.dto;

import lombok.Getter;

@Getter
public class CommentGetResponse {

    private final Long id;
    private final String content;
    private final Long userId;
    private final Long scheduleId;

    public CommentGetResponse(Long id, String content, Long userId, Long scheduleId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.scheduleId = scheduleId;
    }
}
