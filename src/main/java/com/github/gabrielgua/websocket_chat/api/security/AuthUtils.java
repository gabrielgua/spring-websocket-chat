package com.github.gabrielgua.websocket_chat.api.security;

import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final UserService userService;

    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private String getAuthenticatedUsername() {
        return getAuth().getName();
    }

    public boolean isAuthenticatedUser(String username) {
        return username.equals(getAuthenticatedUsername());
    }

    public User getAuthenticatedUser() {
        return userService.findByUsername(getAuthenticatedUsername());
    }
}
