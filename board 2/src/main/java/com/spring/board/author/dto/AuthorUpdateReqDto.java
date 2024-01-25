package com.spring.board.author.dto;

import lombok.Data;

@Data
public class AuthorUpdateReqDto {
    private String name;
    private String password;
}