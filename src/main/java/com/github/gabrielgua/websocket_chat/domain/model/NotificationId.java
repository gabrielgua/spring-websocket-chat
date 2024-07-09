package com.github.gabrielgua.websocket_chat.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class NotificationId {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "chat_id")
    private String chatId;
}
