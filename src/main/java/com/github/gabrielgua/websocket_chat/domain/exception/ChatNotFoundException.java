package com.github.gabrielgua.websocket_chat.domain.exception;

public class ChatNotFoundException extends NotFoundException{
    public ChatNotFoundException(String chatId) {
        super(String.format("Chat not found for id: %s", chatId));
    }
}
