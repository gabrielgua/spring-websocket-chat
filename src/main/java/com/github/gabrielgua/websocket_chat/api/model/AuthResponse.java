package com.github.gabrielgua.websocket_chat.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {

    private Long senderId;
    private String username;
    private String token;
}
