package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.ChatCountResponse;
import com.github.gabrielgua.websocket_chat.api.model.ChatResponse;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.ChatType;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final MessageMapper messageMapper;
    private final UserMapper userMapper;
    private final AuthUtils authUtils;

    public ChatResponse toResponse(Chat chat) {

        var lastMessage = messageMapper.toResponse(chat.getMessages().getLast());
        var statusCount = createChatResponseStatusCount(chat);

        var responseBuilder = ChatResponse.builder()
                .id(chat.getId().toString())
                .name(chat.getName())
                .type(chat.getType())
                .createdAt(chat.getCreatedAt())
                .statusCount(statusCount)
                .lastMessage(lastMessage);

        if (chat.isPrivate()) {
            var receiver = getReceiver(chat);

            receiver.ifPresent(user -> {
                responseBuilder.receiver(userMapper.toResponse(receiver.get()));
                responseBuilder.name(receiver.get().getUsername());
            });
        }

        return responseBuilder.build();
    }

    public ChatResponse toResponseCompact(Chat chat) {
        var statusCount = createChatResponseStatusCount(chat);

        var response = ChatResponse.builder()
                .id(chat.getId().toString())
                .statusCount(statusCount);

        if (chat.isPrivate()) {
            var receiver = getReceiver(chat);

            receiver.ifPresent(user -> {
                response.receiver(userMapper.toResponse(receiver.get()));
            });
        }

        return response.build();
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

    public Optional<User> getReceiver(Chat chat) {
        if (chat.getType().equals(ChatType.GROUP)) {
            return Optional.empty();
        }

        return chat.getUsers().stream()
                .filter(user -> !authUtils.isAuthenticatedUser(user.getUsername()))
                .findFirst();
    }

    public ChatCountResponse createChatResponseStatusCount(Chat chat) {
        return ChatCountResponse.builder()
                .online(getStatusCount(chat, "ONLINE"))
                .offline(getStatusCount(chat, "OFFLINE"))
                .members(chat.getUsers().size())
                .build();
    }
}
