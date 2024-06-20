package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.ChatResponse;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
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
                .build();
    }

    public List<ChatResponse> toCollectionResponse(List<Chat> chats) {
        return chats.stream()
                .map(this::toResponse)
                .toList();
    }
}
