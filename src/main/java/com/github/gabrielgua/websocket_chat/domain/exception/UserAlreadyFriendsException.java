package com.github.gabrielgua.websocket_chat.domain.exception;

public class UserAlreadyFriendsException extends BusinessException{
    public UserAlreadyFriendsException() {
        super("Cannot send requests between users that are already friends");
    }
}
