package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestRequest;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.service.FriendRequestService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper.Type.RECEIVED;
import static com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper.Type.SENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/requests")
public class UserFriendRequestController {

    private final AuthUtils authUtils;
    private final UserService userService;
    private final FriendRequestMapper mapper;
    private final FriendRequestService service;

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

    @PostMapping("/send")
    public ResponseEntity<?> sendRequest(@Valid @RequestBody FriendRequestRequest requestBody) {
        var requester = authUtils.getAuthenticatedUser();
        var receiver = userService.findById(requestBody.getReceiverId());

        service.save(requester, receiver);
        return ResponseEntity.ok("Request sent!");
    }
}
