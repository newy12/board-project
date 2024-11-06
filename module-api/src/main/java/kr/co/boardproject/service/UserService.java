package kr.co.boardproject.service;

import kr.co.boardproject.dto.user.JoinUserReqDto;
import kr.co.boardproject.entity.User;
import kr.co.boardproject.exception.ApiException;
import kr.co.boardproject.repository.UserRepository;
import kr.co.boardproject.utils.AES256Cipher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(JoinUserReqDto joinUserReqDto) throws Exception {
        if(userRepository.existsByUserEmail(AES256Cipher.encrypt(joinUserReqDto.getUserEmail()))){
            throw new ApiException(400,"이미 가입된 이메일입니다.");
        }
        userRepository.save(new User(joinUserReqDto, passwordEncoder));
    }
}
