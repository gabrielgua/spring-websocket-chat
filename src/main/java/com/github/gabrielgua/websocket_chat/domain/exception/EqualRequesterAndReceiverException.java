package com.github.gabrielgua.websocket_chat.domain.exception;

public class EqualRequesterAndReceiverException extends BusinessException{
    public EqualRequesterAndReceiverException() {
        super("Cannot send, accept, reject or cancel a request to itself");
    }
}
