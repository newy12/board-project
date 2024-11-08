package kr.co.boardproject.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "board-user-api")
public interface BoardUserApiFeignClient {

    @GetMapping(value = "/api/v1/user/test")
    String getTest();
}
