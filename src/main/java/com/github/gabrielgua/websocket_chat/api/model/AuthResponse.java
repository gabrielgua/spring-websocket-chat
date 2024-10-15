package com.github.gabrielgua.websocket_chat.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {

    private Long userId;
    private String username;
    private String avatarUrl;
    private String token;

}
