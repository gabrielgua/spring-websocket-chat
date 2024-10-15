package com.github.gabrielgua.websocket_chat.api.security;

import com.github.gabrielgua.websocket_chat.api.model.AuthRequest;
import com.github.gabrielgua.websocket_chat.api.model.AuthResponse;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthResponse authenticate(AuthRequest request) {
        var user = userService.findByUsername(request.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var token = tokenService.generateToken(user);
        return toResponse(user, token);
    }
    public AuthResponse register(User user) {
        var token = tokenService.generateToken(user);
        return toResponse(user, token);
    }

    public AuthResponse toResponse(User user, String token) {
        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .avatarUrl(user.getAvatarUrl())
                .username(user.getUsername())
                .build();
    }
}
