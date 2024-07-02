package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.ChatResponse;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatMapper {

    public ChatResponse toResponse(Chat chat) {
        return ChatResponse.builder()
                .id(chat.getId().toString())
                .name(chat.getName())
                .type(chat.getType())
                .createdAt(chat.getCreatedAt())
                .members(chat.getUsers().size())
                .offline(getStatusCount(chat, "OFFLINE"))
                .online(getStatusCount(chat, "ONLINE"))
                .build();
    }

    public List<ChatResponse> toCollectionResponse(List<Chat> chats) {
        return chats.stream()
                .map(this::toResponse)
                .toList();
    }

    public long getStatusCount(Chat chat, String status) {
        return chat.getUsers().stream()
                .filter(user -> user.getStatus().equals(UserStatus.valueOf(status)))
                .toList()
                .size();
    }
}
