package com.github.gabrielgua.websocket_chat.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gabrielgua.websocket_chat.domain.model.FriendRequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendRequestResponse {

    private UserResponse requester;
    private UserResponse receiver;
    private FriendRequestStatus status;
    private OffsetDateTime createdAt;
}
