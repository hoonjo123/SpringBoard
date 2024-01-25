package com.spring.board.post.dto;

import lombok.Data;

import java.time.LocalDateTime;


    @Data
    public class PostDetailResDto {
        private Long id;
        private String title;
        private String contents;
        private LocalDateTime createdTime;
        private String authorEmail;
    }

