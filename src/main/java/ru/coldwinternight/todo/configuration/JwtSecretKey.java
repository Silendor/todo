package ru.coldwinternight.todo.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class JwtSecretKey {

    private final JwtConfig jwtConfig;

    @Bean
    public Algorithm secretKey() {
        return Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }
}
