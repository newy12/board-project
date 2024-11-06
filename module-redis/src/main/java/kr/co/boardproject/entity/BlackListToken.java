package kr.co.boardproject.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("blackListToken")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackListToken {
    @Id
    private String id;
    @Indexed
    private String accessToken;
    @Indexed
    private String userEmail;

    public BlackListToken(String accessToken, String userEmail) {
        this.accessToken = accessToken;
        this.userEmail = userEmail;
    }
}
