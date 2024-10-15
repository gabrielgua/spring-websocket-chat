package com.github.gabrielgua.websocket_chat.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gabrielgua.websocket_chat.domain.model.ChatType;
import com.github.gabrielgua.websocket_chat.domain.model.Message;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatResponse {
    private String id;
    private String name;
    private String description;
    private ChatType type;
    private OffsetDateTime createdAt;
    private UserResponse receiver;
    private UserResponse creator;
    private ChatCountResponse statusCount;
    private MessageResponse lastMessage;
}
