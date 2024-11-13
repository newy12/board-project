package kr.co.boardproject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigServer
public class ModuleConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModuleConfigServerApplication.class, args);
    }
}
