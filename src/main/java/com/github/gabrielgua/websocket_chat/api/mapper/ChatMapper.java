package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.ChatResponse;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.ChatType;
import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final MessageMapper messageMapper;

    public ChatResponse toResponse(Chat chat) {
        var responseBuilder = ChatResponse.builder()
                .id(chat.getId().toString())
                .name(chat.getName())
                .type(chat.getType())
                .createdAt(chat.getCreatedAt())
                .members(chat.getUsers().size())
                .offline(getStatusCount(chat, "OFFLINE"))
                .online(getStatusCount(chat, "ONLINE"));

        var lastMessage = messageMapper.toResponse(chat.getMessages().getLast());

        return responseBuilder
                .lastMessage(lastMessage)
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
