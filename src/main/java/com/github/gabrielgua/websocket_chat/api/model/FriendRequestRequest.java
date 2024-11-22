package com.github.gabrielgua.websocket_chat.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestRequest {

    @NotNull
    private Long receiverId;

}
