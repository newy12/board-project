package kr.co.boardproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.boardproject.auth.CustomUserDetails;
import kr.co.boardproject.dto.auth.AuthRequestDto;
import kr.co.boardproject.dto.user.TokenResDto;
import kr.co.boardproject.handler.ApiResponse;
import kr.co.boardproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResDto> login(@RequestBody AuthRequestDto authRequestDto) throws Exception {
        return ResponseEntity.ok(authService.loginProcess(authRequestDto));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        authService.logoutProcess(request,customUserDetails);
        return ResponseEntity.ok(ApiResponse.success());
    }
    @PostMapping("/refresh")
    public ResponseEntity<TokenResDto> giveNewAccessToken(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(authService.giveNewAccessToken(request));
    }
}
