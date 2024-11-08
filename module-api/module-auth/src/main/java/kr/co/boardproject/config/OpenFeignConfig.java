package kr.co.boardproject.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(defaultConfiguration = {OpenFeignConfig.class},basePackages = {"kr.co.boardproject.feign"})
public class OpenFeignConfig {
}
