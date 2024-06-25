package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.ChatMapper;
import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.ChatResponse;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.service.ChatService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatService service;
    private final ChatMapper mapper;
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping
    public List<ChatResponse> listAll() {
        return mapper.toCollectionResponse(service.findAll());
    }

    @GetMapping("/users/{userId}")
    public List<ChatResponse> listAllByUser(@PathVariable Long userId) {
        var user = userService.findById(userId);
        return mapper.toCollectionResponse(service.findAllByUser(user));
    }

    @GetMapping("/{chatId}/users")
    public List<UserResponse> findAllUsersByChat(@PathVariable String chatId) {
        var chat = service.findById(chatId);
        return userMapper.toCollectionResponse(chat.getUsers().stream().toList());
    }
}
