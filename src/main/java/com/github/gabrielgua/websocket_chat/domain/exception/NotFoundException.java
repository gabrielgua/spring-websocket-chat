package com.github.gabrielgua.websocket_chat.domain.exception;

public class NotFoundException extends BusinessException{
    public NotFoundException(String message) {
        super(message);
    }
}
