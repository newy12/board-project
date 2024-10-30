package kr.co.boardproject.controller;


import kr.co.boardproject.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @GetMapping("")
    public void get(@AuthenticationPrincipal CustomUserDetails userDetails) {
      log.info("userDetails: {}", userDetails);
    }
}
