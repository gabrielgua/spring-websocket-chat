package com.github.gabrielgua.websocket_chat.domain.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long userId) {
        super(String.format("User not found for id: %d", userId));
    }
}
