package com.advanced.comment.dto;

import lombok.Getter;

@Getter
public class CommentCreateResponse {

    private final Long id;
    private final String content;

    public CommentCreateResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
