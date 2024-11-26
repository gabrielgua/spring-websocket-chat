package com.github.gabrielgua.websocket_chat.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestRequester {

    @NotNull
    private Long requesterId;
}
