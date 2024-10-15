package com.github.gabrielgua.websocket_chat.api.security;

import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.AuthRequest;
import com.github.gabrielgua.websocket_chat.api.model.AuthResponse;
import com.github.gabrielgua.websocket_chat.api.model.UserRequest;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
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
