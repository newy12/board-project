package kr.co.boardproject.dto.auth;

import lombok.Data;

@Data
public class RefreshNewTokenDto {
    private String accessToken;
    private Long refreshTokenId;
}
