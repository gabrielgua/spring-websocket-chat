package com.github.gabrielgua.websocket_chat.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationResponse {

    private Long id;
    private String chatId;
    private long count;
}
