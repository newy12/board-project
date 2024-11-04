package kr.co.boardproject.dto.auth;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String userEmail;
    private String userPassword;
}
