package com.github.gabrielgua.websocket_chat.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FriendRequestId {

    private Long requesterId;
    private Long receiverId;


}
