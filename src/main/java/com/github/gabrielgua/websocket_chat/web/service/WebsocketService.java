package com.github.gabrielgua.websocket_chat.web.service;

import com.github.gabrielgua.websocket_chat.api.model.FriendRequestResponse;
import com.github.gabrielgua.websocket_chat.api.model.MessageResponse;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.service.ChatService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.gabrielgua.websocket_chat.domain.model.UserStatus.ONLINE;

@Service
@RequiredArgsConstructor
public class WebsocketService {

    private static final String CHAT_TOPIC = "/topic/chats";
    private final ChatService chatService;

    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessageToChat(String chatId, MessageResponse message) {
        messagingTemplate.convertAndSend(String.format("%s/%s", CHAT_TOPIC, chatId), message);
    }
    public void sendRequestNotification(User receiver, FriendRequestResponse response) {
        messagingTemplate.convertAndSendToUser(receiver.getUsername(), "/request-notifications", response);
    }

    public void sendUserConnectionNotification(User receiver, UserResponse response) {
        messagingTemplate.convertAndSendToUser(receiver.getUsername(), "/connection-notifications", response);
    }

    @Transactional(readOnly = true)
    public void sendUserConnectionNotificationToFriends(User user, UserResponse response) {
        user.getFriends()
                .stream()
                .filter(friend -> friend.getStatus() == ONLINE)
                .forEach(onlineFriend -> sendUserConnectionNotification(onlineFriend, response));
    }

    @Transactional(readOnly = true)
    public void sendUserConnectionNotificationToChats(User user, UserResponse response) {
        var chats = chatService.findAllByUser(user);

        chats.stream()
                .filter(Chat::isGroup)
                .forEach(chat -> messagingTemplate.convertAndSend(String.format("%s/%s", CHAT_TOPIC, chat.getId()), response));
    }
}
