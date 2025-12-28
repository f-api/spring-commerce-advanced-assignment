package com.advanced.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @NotBlank
    private String content;
}
