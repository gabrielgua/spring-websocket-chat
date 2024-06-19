package com.github.gabrielgua.websocket_chat.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class MessageResponse {

    private Long id;
    private String chatId;
    private Long senderId;
    private String content;
    private OffsetDateTime timestamp;
}
