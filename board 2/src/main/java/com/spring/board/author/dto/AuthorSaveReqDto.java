package com.spring.board.author.dto;

import lombok.Data;


@Data// getter setter toString 전부 포함되어있음
public class AuthorSaveReqDto {
    private String name;
    private String email;
    private String password;
    private String role;
    }
