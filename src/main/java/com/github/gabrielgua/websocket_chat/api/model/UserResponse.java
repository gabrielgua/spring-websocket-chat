package com.github.gabrielgua.websocket_chat.api.model;

import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private UserStatus status;
}
