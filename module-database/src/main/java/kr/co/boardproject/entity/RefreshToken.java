package kr.co.boardproject.entity;

import jakarta.persistence.*;
import kr.co.boardproject.entity.common.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public RefreshToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public void updateToken(String refreshToken) {
        this.token = refreshToken;
    }
}
