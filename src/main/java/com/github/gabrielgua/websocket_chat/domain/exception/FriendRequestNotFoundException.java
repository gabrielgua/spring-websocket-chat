package com.github.gabrielgua.websocket_chat.domain.exception;

import com.github.gabrielgua.websocket_chat.domain.model.FriendRequestId;

public class FriendRequestNotFoundException extends NotFoundException{
    public FriendRequestNotFoundException(FriendRequestId requestId) {
        super(String.format("Request not found for {requester: %d, receiver: %d}", requestId.getRequesterId(), requestId.getReceiverId()));
    }

}
