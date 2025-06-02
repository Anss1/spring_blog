package com.anas.springblog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDTO {
    private Long id;
    private String text;
    private String authorUsername;
    private LocalDateTime createdAt;

    public CommentResponseDTO() {}

    public CommentResponseDTO(Long id, String text, String authorUsername, LocalDateTime createdAt) {
        this.id = id;
        this.text = text;
        this.authorUsername = authorUsername;
        this.createdAt = createdAt;
    }
}
