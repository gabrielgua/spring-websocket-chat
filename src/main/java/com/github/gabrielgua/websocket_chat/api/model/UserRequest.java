package com.github.gabrielgua.websocket_chat.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
