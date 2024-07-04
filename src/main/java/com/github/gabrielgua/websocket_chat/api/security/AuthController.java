package com.github.gabrielgua.websocket_chat.api.security;

import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.AuthRequest;
import com.github.gabrielgua.websocket_chat.api.model.AuthResponse;
import com.github.gabrielgua.websocket_chat.api.model.UserRequest;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.repository.UserRepository;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid UserRequest request) {
        var user = userMapper.toEntity(request);
        return authService.register(userService.save(user));
    }
}
