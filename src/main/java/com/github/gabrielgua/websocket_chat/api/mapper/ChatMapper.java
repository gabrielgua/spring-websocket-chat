package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.*;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMapper {

    private final MessageMapper messageMapper;
    private final UserMapper userMapper;
    private final AuthUtils authUtils;

    public ChatResponse toResponse(Chat chat) {
        var lastMessage = getLastMessage(chat);
        var statusCount = createChatResponseStatusCount(chat);

        var response = ChatResponse.builder()
                .id(chat.getId().toString())
                .name(chat.getName())
                .description(chat.getDescription())
                .type(chat.getType())
                .createdAt(chat.getCreatedAt())
                .lastMessage(lastMessage)
                .statusCount(statusCount)
                .creator(userMapper.toResponse(chat.getCreator()));


        getReceiver(chat).ifPresent(user -> {
            response.name(user.getUsername())
                    .receiver(userMapper.toResponse(user))
                    .statusCount(null);
        });

        return response.build();
    }

    public ChatResponse toResponseStatus(Chat chat) {
        var statusCount = createChatResponseStatusCount(chat);

        var response = ChatResponse.builder()
                .id(chat.getId().toString())
                .statusCount(statusCount);


        getReceiver(chat).ifPresent(user -> {
            response.receiver(userMapper.toResponse(user))
                    .statusCount(null);
        });

        return response.build();
    }

    public List<ChatResponse> toCollectionResponse(List<Chat> chats) {
        return chats.stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ChatResponse> toCollectionResponseStatus(List<Chat> chats) {
        return chats.stream()
                .map(this::toResponseStatus)
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

    private MessageResponse getLastMessage(Chat chat) {
        if (chat.getMessages().isEmpty()) {
            return MessageResponse.builder().build();
        }

        return messageMapper.toCompactResponse(chat.getMessages().getLast());
    }

    public Chat toEntity(ChatRequest request, List<User> users, User creator) {
        var chat = new Chat();

        chat.setId(UUID.randomUUID());
        chat.setCreator(creator);
        chat.setType(request.getType());
        chat.setName(request.getName());
        chat.setDescription(request.getDescription());
        chat.setUsers(new HashSet<>(users));
        chat.setCreatedAt(OffsetDateTime.now());

        return chat;
    }

}
