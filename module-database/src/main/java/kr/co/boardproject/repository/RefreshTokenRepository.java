package kr.co.boardproject.repository;

import kr.co.boardproject.entity.RefreshToken;
import kr.co.boardproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByUser(User user);
}
