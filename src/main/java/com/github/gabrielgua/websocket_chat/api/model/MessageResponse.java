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
    private String chat;
    private String sender;
    private String content;
    private OffsetDateTime timestamp;
}
