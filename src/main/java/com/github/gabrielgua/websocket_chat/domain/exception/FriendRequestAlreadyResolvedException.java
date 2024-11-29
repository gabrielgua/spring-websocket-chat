package com.github.gabrielgua.websocket_chat.domain.exception;

public class FriendRequestAlreadyResolvedException extends BusinessException{
    public FriendRequestAlreadyResolvedException() {
        super("The request is already resolved and cannot be canceled or accepted");
    }

}
