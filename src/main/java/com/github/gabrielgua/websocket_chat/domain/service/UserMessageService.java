package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.Message;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMessageService {

    private final UserService userService;

    @Transactional
    public void addUnreadToOffline(Chat chat, Message message) {
        var offlineUsers = chat.getUsers().stream()
                .filter(user -> user.getStatus().equals(UserStatus.OFFLINE))
                .toList();

        offlineUsers.forEach(user -> {
            addMessageToUnreadList(user, message);
        });
    }

    @Transactional
    public void addMessageToUnreadList(User user, Message message) {
        user.addUnread(message);
        userService.save(user);
    }

    @Transactional
    public void removeUnreadMessages(User user, List<Message> messages) {
        user.removeUnreadList(messages);
        userService.save(user);
    }


}
