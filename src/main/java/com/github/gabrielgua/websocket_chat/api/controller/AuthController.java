package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.model.AuthRequest;
import com.github.gabrielgua.websocket_chat.api.model.AuthResponse;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.repository.UserRepository;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        var user = repository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(() -> new RuntimeException("Invalid username and/or password"));

        return AuthResponse.builder()
                .senderId(user.getId())
                .username(user.getUsername())
                .token(String.format("%s-%s", user.getUsername(), UUID.randomUUID()))
                .build();
    }
}
