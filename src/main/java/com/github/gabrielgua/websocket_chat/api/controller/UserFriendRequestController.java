package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper;
import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestReceiver;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestRequester;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.service.FriendRequestService;
import com.github.gabrielgua.websocket_chat.domain.service.UserFriendService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import com.github.gabrielgua.websocket_chat.web.service.WebsocketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final WebsocketService wsService;
    private final UserFriendService friendService;
    private final FriendRequestService requestService;
    private final FriendRequestMapper requestMapper;
    private final UserMapper userMapper;

    @GetMapping("/sent")
    public List<FriendRequestResponse> listSent() {
        var authUser = authUtils.getAuthenticatedUser();
        return requestMapper.toCollectionResponse(authUser.getSentRequests().stream().toList(), SENT);
    }

    @GetMapping("/received")
    public List<FriendRequestResponse> listReceived() {
        var authUser = authUtils.getAuthenticatedUser();
        return requestMapper.toCollectionResponse(authUser.getReceivedRequests().stream().toList(), RECEIVED);
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public FriendRequestResponse sendRequest(@Valid @RequestBody FriendRequestReceiver receiverRequest) {
        var requester = authUtils.getAuthenticatedUser();
        var receiver = userService.findById(receiverRequest.getReceiverId());

        var request = requestService.save(requester, receiver);
        var receiverResponse = requestMapper.toResponseReceived(request);
        var requesterResponse = requestMapper.toResponseSent(request);

        wsService.sendRequestNotification(receiver, receiverResponse);
        return requesterResponse;
    }

    @PutMapping("/accept")
    public ResponseEntity<?> acceptRequest(@Valid @RequestBody FriendRequestRequester requesterRequest) {
        var receiver = authUtils.getAuthenticatedUser();
        var requester = userService.findById(requesterRequest.getRequesterId());

        var request = requestService.findById(requester.getId(), receiver.getId());
        requestService.accept(request);

        friendService.addFriend(requester.getId());

        var receiverResponse = requestMapper.toResponseSent(request);
        wsService.sendRequestNotification(requester, receiverResponse);

        return ResponseEntity.ok(userMapper.toResponse(requester));
    }

    @DeleteMapping("/deny")
    public ResponseEntity<?> denyRequest(@Valid @RequestBody FriendRequestRequester requesterRequest) {
        var receiver = authUtils.getAuthenticatedUser();

        var requester = userService.findById(requesterRequest.getRequesterId());
        var request = requestService.findById(requester.getId(), receiver.getId());
        requestService.deny(request);

        return ResponseEntity.ok("Request rejected!");
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelRequest(@Valid @RequestBody FriendRequestReceiver receiverRequest) {
        var user = authUtils.getAuthenticatedUser();
        var receiver = userService.findById(receiverRequest.getReceiverId());
        var request = requestService.findById(user.getId(), receiver.getId());
        requestService.cancel(request);

        return ResponseEntity.ok("Request canceled!");
    }

}
