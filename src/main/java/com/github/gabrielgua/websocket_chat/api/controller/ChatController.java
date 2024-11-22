package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.ChatMapper;
import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.ChatRequest;
import com.github.gabrielgua.websocket_chat.api.model.ChatResponse;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.ChatType;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.service.ChatService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatMapper mapper;
    private final ChatService chatService;
    private final UserMapper userMapper;
    private final AuthUtils authUtils;
    private final UserService userService;

    @GetMapping
    public List<ChatResponse> listAllByUser() {
        var user = authUtils.getAuthenticatedUser();
        var chats = chatService.findAllByUser(user);
        return mapper.toCollectionResponse(chats);
    }

    @GetMapping("/{chatId}/users")
    public List<UserResponse> findAllUsersByChat(@PathVariable String chatId) {
        var chat = chatService.findById(chatId);
        return userMapper.toCollectionResponse(chat.getUsers().stream().toList());
    }

    @GetMapping("/status")
    public List<ChatResponse> listStatuses() {
        var user = authUtils.getAuthenticatedUser();
        return mapper.toCollectionResponseStatus(chatService.findAllByUser(user));
    }

    @GetMapping("/{chatId}/status")
    public ChatResponse countChatUsersWithStatus(@PathVariable String chatId) {
        return mapper.toResponseStatus(chatService.findById(chatId));
    }

    @PostMapping
    public ChatResponse createNew(@Valid @RequestBody ChatRequest request) {
        List<User> users = request.getUsers().stream()
                .map(userService::findById)
                .toList();

        var creator = authUtils.getAuthenticatedUser();
        var chat = mapper.toEntity(request, users, creator);

        return mapper.toResponse(chatService.save(chat));
    }


}
