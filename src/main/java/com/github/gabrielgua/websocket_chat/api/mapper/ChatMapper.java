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
                .imageUrl(chat.getImageUrl())
                .createdAt(chat.getCreatedAt())
                .lastMessage(lastMessage)
                .statusCount(statusCount)
                .creator(userMapper.toResponse(chat.getCreator()));


        getReceiver(chat).ifPresent(user -> {
            response.name(user.getUsername())
                    .receiver(userMapper.toResponse(user))
                    .imageUrl(user.getAvatarUrl())
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
        if (chat.getMessages() == null || chat.getMessages().isEmpty()) {
            return MessageResponse.builder().build();
        }

        return messageMapper.toCompactResponse(chat.getMessages().getLast());
    }

    public Chat toEntity(ChatRequest request, List<User> users, User creator) {
        return Chat.builder()
                .creator(creator)
                .type(request.getType())
                .users(new HashSet<>(users))
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(OffsetDateTime.now())
                .build();
    }

    public Chat toEntity(User requester, User receiver) {
        return Chat.builder()
                .id(UUID.randomUUID())
                .creator(requester)
                .type(ChatType.PRIVATE)
                .name(String.format("%s, %s", requester.getUsername(), receiver.getUsername()))
                .createdAt(OffsetDateTime.now())
                .users(Set.of(requester, receiver))
                .build();
    }

}