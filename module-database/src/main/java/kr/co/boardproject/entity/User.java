package kr.co.boardproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.co.boardproject.dto.user.JoinUserReqDto;
import kr.co.boardproject.entity.common.OnlyDateBaseEntity;
import kr.co.boardproject.utils.AES256Cipher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends OnlyDateBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userEmail;
    private String userName;
    private String userNickname;
    private String userPassword;

    public User(JoinUserReqDto joinUserReqDto, PasswordEncoder passwordEncoder) throws Exception {
        this.userEmail = AES256Cipher.encrypt(joinUserReqDto.getUserEmail());
        this.userName = joinUserReqDto.getUserName();
        this.userNickname = joinUserReqDto.getUserNickname();
        this.userPassword = passwordEncoder.encode(joinUserReqDto.getUserPassword());
    }
}
