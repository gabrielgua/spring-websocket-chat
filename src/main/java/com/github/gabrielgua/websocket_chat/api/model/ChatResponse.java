package com.github.gabrielgua.websocket_chat.api.model;

import com.github.gabrielgua.websocket_chat.domain.model.ChatType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class ChatResponse {
    private String id;
    private String name;
    private ChatType type;
    private OffsetDateTime createdAt;
}
