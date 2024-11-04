package kr.co.boardproject.dto.user;

import lombok.Data;

@Data
public class JoinUserReqDto {
    private String userEmail;
    private String userName;
    private String userNickname;
    private String userPassword;
}
