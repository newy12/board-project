package kr.co.boardproject.filter;

import kr.co.boardproject.repository.BlackListTokenRepository;
import kr.co.boardproject.utils.JwtTokenUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtAuthFilterFactory extends AbstractGatewayFilterFactory<JwtAuthFilterFactory.Config> {

    @Autowired
    private  BlackListTokenRepository blackListTokenRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            if (path.endsWith("/join")) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);
            try {
                if(blackListTokenRepository.existsByAccessToken(token)) {
                    throw new Exception("블랙리스트에 들어있는 토큰입니다.");
                }
                jwtTokenUtil.validateTokenAndGetUserEmail(token);
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    @Setter
    @Getter

    public static class Config {
        private String secretKey;

    }
}
