package kr.co.boardproject.controller;

import kr.co.boardproject.dto.AuthRequestDto;
import kr.co.boardproject.dto.AuthResponseDto;
import kr.co.boardproject.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword())
            );

            String token = jwtTokenUtil.generateAccessToken(authRequestDto.getUsername());
            return ResponseEntity.ok(new AuthResponseDto(token));

    }
}
