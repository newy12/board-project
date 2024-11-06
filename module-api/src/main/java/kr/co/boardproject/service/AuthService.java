package kr.co.boardproject.service;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.boardproject.auth.CustomUserDetails;
import kr.co.boardproject.dto.auth.NewAccessTokenResDto;
import kr.co.boardproject.dto.auth.AuthRequestDto;
import kr.co.boardproject.dto.user.TokenResDto;
import kr.co.boardproject.entity.BlackListToken;
import kr.co.boardproject.entity.RefreshToken;
import kr.co.boardproject.entity.User;
import kr.co.boardproject.exception.ApiException;
import kr.co.boardproject.repository.BlackListTokenRepository;
import kr.co.boardproject.repository.RefreshTokenRepository;
import kr.co.boardproject.repository.UserRepository;
import kr.co.boardproject.utils.AES256Cipher;
import kr.co.boardproject.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BlackListTokenRepository blackListTokenRepository;

    @Transactional
    public TokenResDto loginProcess(AuthRequestDto authRequestDto) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUserEmail(), authRequestDto.getUserPassword()));

        //엑세스 토큰 생성
        String accessToken = jwtTokenUtil.generateAccessToken(authRequestDto.getUserEmail());
        //리프레시 토큰 생성
        String refreshToken = jwtTokenUtil.generateRefreshToken(authRequestDto.getUserEmail());

        //기존 정보가 있다면 리프레시 토큰 업데이트
        User user = userRepository.findByUserEmail(AES256Cipher.encrypt(authRequestDto.getUserEmail()))
                .orElseThrow(() -> new ApiException(500, "사용자 정보가 존재하지 않습니다."));

        RefreshToken refreshTokenByUser = refreshTokenRepository.findByUser(user).orElse(null);
        if (refreshTokenByUser != null) {
            refreshTokenByUser.updateToken(refreshToken);
            return new TokenResDto(accessToken, refreshTokenByUser.getRefreshTokenId());
        } else {
            RefreshToken refreshTokenInfo = refreshTokenRepository.save(new RefreshToken(refreshToken, user));
            return new TokenResDto(accessToken, refreshTokenInfo.getRefreshTokenId());
        }
    }

    @Transactional
    public void logoutProcess(HttpServletRequest request, CustomUserDetails customUserDetails) throws Exception {
        //해당 유저의 과거 블랙리스트에 들어있는 토큰 제거
        blackListTokenRepository.findByUserEmail(AES256Cipher.decrypt(customUserDetails.getUserEmail()))
                .ifPresent(blackListInfo -> blackListTokenRepository.deleteById(blackListInfo.getId()));
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 토큰 부분만 추출
            String accessToken = authorizationHeader.substring(7); // "Bearer " 부분을 제거
            blackListTokenRepository.save(new BlackListToken(accessToken, AES256Cipher.decrypt(customUserDetails.getUserEmail())));
        } else {
            throw new ApiException(400, "로그아웃에 실패했습니다.");
        }
    }

    @Transactional
    public TokenResDto giveNewAccessToken(HttpServletRequest request) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        String refreshTokenId = request.getHeader("Refresh-Token-Id");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Bearer 토큰 부분만 추출
            String accessToken = authorizationHeader.substring(7);
            if (jwtTokenUtil.validateTokenAndGetUserEmail(accessToken) != null) {
                throw new ApiException(400, "토큰이 아직 유효합니다.");
            } else {
                RefreshToken rtInfo = refreshTokenRepository.findById(Long.valueOf(refreshTokenId))
                        .orElseThrow(() -> new ApiException(400, "리프레시 토큰 정보가 존재하지 않습니다."));

                if (jwtTokenUtil.validateTokenAndGetUserEmail(rtInfo.getToken()) != null) {

                    String newAccessToken = jwtTokenUtil.generateAccessToken(AES256Cipher.decrypt(rtInfo.getUser().getUserEmail()));
                    String newRefreshToken = jwtTokenUtil.generateRefreshToken(AES256Cipher.decrypt(rtInfo.getUser().getUserEmail()));

                    //새로 갱신한 refresh token으로 업데이트
                    rtInfo.updateToken(newRefreshToken);
                    return new TokenResDto(newAccessToken,rtInfo.getRefreshTokenId());
                } else {
                    throw new ApiException(400, "리프레시 토큰이 유효하지 않습니다.");
                }
            }
        } else {
            throw new ApiException(400, "토큰이 존재하지 않습니다.");
        }

    }
}
