package kr.co.boardproject.controller;

import lombok.Data;

@Data
public class UserReqDto {
    private String userEmail;
    private String userName;
    private String userPassword;
}
