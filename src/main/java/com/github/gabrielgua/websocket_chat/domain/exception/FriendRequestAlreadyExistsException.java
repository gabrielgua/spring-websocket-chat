package com.github.gabrielgua.websocket_chat.domain.exception;

public class FriendRequestAlreadyExistsException extends BusinessException{
    public FriendRequestAlreadyExistsException() {
        super("A friend request already exists between these users");
    }
}
