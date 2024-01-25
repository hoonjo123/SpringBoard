package com.spring.board.author.dto;

import lombok.Data;



@Data
public class AuthorSaveReqDto {
    private String name;
    private String email;
    private String password;
    private String role;
}