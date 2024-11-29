package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.FriendRequestResponse;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.domain.model.FriendRequest;
import com.github.gabrielgua.websocket_chat.domain.model.FriendRequestId;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendRequestMapper {

    public enum Type {
        SENT, RECEIVED
    }



    private final UserService userService;
    private final UserMapper userMapper;

    public FriendRequestResponse.FriendRequestResponseBuilder toResponse(FriendRequest request) {
        return FriendRequestResponse.builder()
                .id(new FriendRequestId(request.getId().getRequesterId(), request.getId().getReceiverId()))
                .status(request.getStatus())
                .createdAt(request.getCreatedAt());
    }

    public FriendRequestResponse toResponseSent(FriendRequest request) {
        var receiver = userService.findById(request.getId().getReceiverId());
        return toResponse(request).receiver(userMapper.toResponse(receiver)).build();
    }

    public FriendRequestResponse toResponseReceived(FriendRequest request) {
        var requester = userService.findById(request.getId().getRequesterId());
        return toResponse(request).requester(userMapper.toResponse(requester)).build();
    }

    public List<FriendRequestResponse> toCollectionResponse(List<FriendRequest> requests, Type type) {
        if (type == Type.SENT) {
            return requests.stream().map(this::toResponseSent).toList();
        }

        return requests.stream().map(this::toResponseReceived).toList();
    }
}
