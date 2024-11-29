package com.github.gabrielgua.websocket_chat.domain.exception;

public class NotRequesterException extends BusinessException{
    public NotRequesterException() {
        super("Only the requester user can cancel a request");
    }
}
