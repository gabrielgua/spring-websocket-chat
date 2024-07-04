package com.github.gabrielgua.websocket_chat.api.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("api.security")
public class AuthProperties {

    private Token token;

    @Getter
    @Setter
    public static class Token {
        @NotBlank
        private String secret;
        @NotNull
        private long expiration;
    }
}
