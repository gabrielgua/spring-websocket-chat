package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper.Type.RECEIVED;
import static com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper.Type.SENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/friends/requests")
public class UserFriendRequestController {

    private final FriendRequestMapper mapper;
    private final UserService userService;
    private final AuthUtils authUtils;

    @GetMapping("/sent")
    public List<FriendRequestResponse> listSent() {
        var authUser = authUtils.getAuthenticatedUser();
        return mapper.toCollectionResponse(authUser.getSentRequests().stream().toList(), SENT);
    }

    @GetMapping("/received")
    public List<FriendRequestResponse> listReceived() {
        var authUser = authUtils.getAuthenticatedUser();
        return mapper.toCollectionResponse(authUser.getReceivedRequests().stream().toList(), RECEIVED);
    }


}
