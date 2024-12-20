package kr.co.boardproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.boardproject.auth.CustomUserDetails;
import kr.co.boardproject.dto.auth.AuthRequestDto;
import kr.co.boardproject.dto.user.TokenResDto;
import kr.co.boardproject.handler.ApiResponse;
import kr.co.boardproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @Value("${test}")
    private String test;

    @PostMapping("/login")
    public ResponseEntity<TokenResDto> login(@RequestBody AuthRequestDto authRequestDto) throws Exception {
        return ResponseEntity.ok(authService.loginProcess(authRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResDto> giveNewAccessToken(HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(authService.giveNewAccessToken(request));
    }

    @GetMapping("/test")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(authService.test());
    }
}
