package kr.co.boardproject.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewAccessTokenResDto {
    private String accessToken;
    private Long refreshTokenId;
}
