package kr.co.boardproject.service;

import kr.co.boardproject.auth.CustomUserDetails;
import kr.co.boardproject.entity.User;
import kr.co.boardproject.exception.ApiException;
import kr.co.boardproject.repository.UserRepository;
import kr.co.boardproject.utils.AES256Cipher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findByUserEmail((AES256Cipher.encrypt(userEmail)))
                    .orElseThrow(() -> new ApiException(400,"유저 정보를 찾을 수없습니다."));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CustomUserDetails(user.getUserName(), user.getUserPassword(), user.getUserEmail(), user.getUserNickname(), List.of());
    }
}
