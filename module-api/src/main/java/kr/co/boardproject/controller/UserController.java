package kr.co.boardproject.controller;

import kr.co.boardproject.dto.user.JoinUserReqDto;
import kr.co.boardproject.handler.ApiResponse;
import kr.co.boardproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @Comment("회원가입 API")
    public ResponseEntity<?> join(@RequestBody JoinUserReqDto joinUserReqDto) throws Exception {
        userService.join(joinUserReqDto);
        return ResponseEntity.ok(ApiResponse.success());
    }
    @GetMapping("/test")
    public void get(){
        log.info("test");
    }
}
