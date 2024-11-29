package com.github.gabrielgua.websocket_chat.domain.exception;

public class MessageNotFoundException extends NotFoundException{
    public MessageNotFoundException(Long messageId) {
        super(String.format("Message not found for id: %d", messageId));
    }
}
