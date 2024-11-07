package kr.co.boardproject.service;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.boardproject.dto.user.JoinUserReqDto;
import kr.co.boardproject.entity.BlackListToken;
import kr.co.boardproject.entity.User;
import kr.co.boardproject.exception.ApiException;
import kr.co.boardproject.repository.BlackListTokenRepository;
import kr.co.boardproject.repository.UserRepository;
import kr.co.boardproject.utils.AES256Cipher;
import kr.co.boardproject.utils.JwtTokenUtil;
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
    private final BlackListTokenRepository blackListTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public void join(JoinUserReqDto joinUserReqDto) throws Exception {
        if(userRepository.existsByUserEmail(AES256Cipher.encrypt(joinUserReqDto.getUserEmail()))){
            throw new ApiException(400,"이미 가입된 이메일입니다.");
        }
        userRepository.save(new User(joinUserReqDto, passwordEncoder));
    }

    @Transactional
    public void logout(HttpServletRequest request) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 토큰 부분만 추출
            String accessToken = authorizationHeader.substring(7); // "Bearer " 부분을 제거

            String userEmail = jwtTokenUtil.validateTokenAndGetUserEmail(accessToken);

            blackListTokenRepository.findByUserEmail(AES256Cipher.encrypt(userEmail))
                    .ifPresent(blackListInfo -> blackListTokenRepository.deleteById(blackListInfo.getId()));

            blackListTokenRepository.save(new BlackListToken(accessToken, AES256Cipher.encrypt(userEmail)));
        } else {
            throw new ApiException(400, "로그아웃에 실패했습니다.");
        }
    }

}
