package kr.co.boardproject.repository;

import kr.co.boardproject.entity.BlackListToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlackListTokenRepository extends CrudRepository<BlackListToken, String> {
    boolean existsByAccessToken(String accessToken);

    Optional<BlackListToken> findByUserEmail(String decrypt);
}
