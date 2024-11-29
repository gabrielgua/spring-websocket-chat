package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.UserConnectionRequest;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import com.github.gabrielgua.websocket_chat.web.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;
    private final AuthUtils auth;

    @GetMapping
    public List<UserResponse> searchByNameOrUsername(@Param("term") String term) {
        return mapper.toCollectionResponse(service.findByUsernameOrNameContaining(term))
                .stream()
                .filter(user -> !Objects.equals(user.getId(), auth.getAuthenticatedUser().getId()))
                .toList();
    }
}
