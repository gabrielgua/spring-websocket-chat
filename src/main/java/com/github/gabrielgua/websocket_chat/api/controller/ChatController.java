package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.ChatMapper;
import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.ChatCountResponse;
import com.github.gabrielgua.websocket_chat.api.model.ChatResponse;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.service.ChatService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import com.github.gabrielgua.websocket_chat.infra.specs.ChatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatMapper mapper;
    private final ChatService service;
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthUtils authUtils;

    @GetMapping
    public List<ChatResponse> listAllByUser() {
        var user = userService.findByUsername(authUtils.getAuthenticatedUsername());
        return mapper.toCollectionResponse(service.findAllByUser(user));
    }

    @GetMapping("/{chatId}/users")
    public List<UserResponse> findAllUsersByChat(@PathVariable String chatId) {
        var chat = service.findById(chatId);
        return userMapper.toCollectionResponse(chat.getUsers().stream().toList());
    }

    @GetMapping("/{chatId}/users/count")
    public ChatCountResponse countChatUsersWithStatus(@PathVariable String chatId) {
        var chat = service.findById(chatId);
        return ChatCountResponse.builder()
                .online(mapper.getStatusCount(chat, "ONLINE"))
                .offline(mapper.getStatusCount(chat, "OFFLINE"))
                .members(chat.getUsers().size())
                .build();
    }
}
