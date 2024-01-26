package com.spring.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class PostListResDto {
        private Long id;
        private String title;
        private String authorEmail;

}
