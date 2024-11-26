package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestReceiver;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestRequester;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.FriendRequestStatus;
import com.github.gabrielgua.websocket_chat.domain.service.FriendRequestService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import com.github.gabrielgua.websocket_chat.web.service.WebsocketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper.Type.RECEIVED;
import static com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper.Type.SENT;
import static com.github.gabrielgua.websocket_chat.domain.model.FriendRequestStatus.ACCEPTED;
import static com.github.gabrielgua.websocket_chat.domain.model.FriendRequestStatus.REJECTED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/requests")
public class UserFriendRequestController {

    private final AuthUtils authUtils;
    private final UserService userService;
    private final FriendRequestMapper mapper;
    private final FriendRequestService service;
    private final WebsocketService wsService;

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
    public ResponseEntity<?> sendRequest(@Valid @RequestBody FriendRequestReceiver requestBody) {
        var requester = authUtils.getAuthenticatedUser();
        var receiver = userService.findById(requestBody.getReceiverId());

        var response = mapper.toResponseReceived(service.save(requester, receiver));
        wsService.sendRequestNotification(receiver, response);
        return ResponseEntity.ok("Request sent!");
    }

    @PutMapping("/accept")
    public ResponseEntity<?> acceptRequest(@Valid @RequestBody FriendRequestRequester requestBody) {
        var receiver = authUtils.getAuthenticatedUser();
        var requester = userService.findById(requestBody.getRequesterId());

        var request = service.findByIds(requester.getId(), receiver.getId());
        service.accept(request);

        return ResponseEntity.ok("Request accepted!");
    }

    @DeleteMapping("/reject")
    public ResponseEntity<?> rejectRequest(@Valid @RequestBody FriendRequestRequester requestBody) {
        var receiver = authUtils.getAuthenticatedUser();
        var requester = userService.findById(requestBody.getRequesterId());

        var request = service.findByIds(requester.getId(), receiver.getId());
        service.reject(request);

        return ResponseEntity.ok("Request rejected!");
    }
}
