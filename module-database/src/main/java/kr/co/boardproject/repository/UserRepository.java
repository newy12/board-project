package kr.co.boardproject.repository;

import kr.co.boardproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);
}
