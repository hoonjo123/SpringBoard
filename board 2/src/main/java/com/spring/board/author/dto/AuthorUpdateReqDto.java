package com.spring.board.author.dto;

import lombok.Data;

@Data
public class AuthorUpdateReqDto {
    private Long id;
    private String name;
    private String password;
    // 기타 필드

    // getters and setters
}
