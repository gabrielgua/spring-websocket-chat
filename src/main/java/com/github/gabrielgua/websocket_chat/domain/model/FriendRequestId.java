package com.github.gabrielgua.websocket_chat.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class FriendRequestId {

    private Long requesterId;
    private Long receiverId;
}
