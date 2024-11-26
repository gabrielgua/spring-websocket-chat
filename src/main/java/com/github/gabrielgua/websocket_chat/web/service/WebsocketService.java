package com.github.gabrielgua.websocket_chat.web.service;

import com.github.gabrielgua.websocket_chat.api.model.FriendRequestResponse;
import com.github.gabrielgua.websocket_chat.api.model.MessageResponse;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.github.gabrielgua.websocket_chat.domain.model.UserStatus.ONLINE;

@Service
@RequiredArgsConstructor
public class WebsocketService {

    private static final String CHAT_TOPIC = "/topic/chats/";

    private final SimpMessagingTemplate messagingTemplate;

    public void sendChatMessage(String chatId, MessageResponse message) {
        messagingTemplate.convertAndSend(CHAT_TOPIC + chatId, message);
    }
    public void sendRequestNotification(User receiver, FriendRequestResponse response) {
        messagingTemplate.convertAndSendToUser(receiver.getUsername(), "/request-notifications", response);
    }
}
