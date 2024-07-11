package com.github.gabrielgua.websocket_chat.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MessageRequest {

    private String chatId;
    private String content;
}
