package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.ChatMapper;
import com.github.gabrielgua.websocket_chat.api.mapper.FriendRequestMapper;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestReceiver;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestRequester;
import com.github.gabrielgua.websocket_chat.api.model.FriendRequestResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.service.ChatService;
import com.github.gabrielgua.websocket_chat.domain.service.FriendRequestService;
import com.github.gabrielgua.websocket_chat.domain.service.UserFriendService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import com.github.gabrielgua.websocket_chat.web.service.WebsocketService;
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
    private final ChatService chatService;
    private final WebsocketService wsService;
    private final UserFriendService friendService;
    private final FriendRequestService requestService;
    private final ChatMapper chatMapper;
    private final FriendRequestMapper requestMapper;

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
    public ResponseEntity<?> sendRequest(@Valid @RequestBody FriendRequestReceiver requestBody) {
        var requester = authUtils.getAuthenticatedUser();
        var receiver = userService.findById(requestBody.getReceiverId());

        var response = requestMapper.toResponseReceived(requestService.save(requester, receiver));
        wsService.sendRequestNotification(receiver, response);
        return ResponseEntity.ok("Request sent!");
    }

    @PutMapping("/accept")
    public ResponseEntity<?> acceptRequest(@Valid @RequestBody FriendRequestRequester requestBody) {
        var receiver = authUtils.getAuthenticatedUser();
        var requester = userService.findById(requestBody.getRequesterId());

        var request = requestService.findById(requester.getId(), receiver.getId());
        requestService.accept(request);



        friendService.addFriend(requester.getId());

        return ResponseEntity.ok("Request accepted!");
    }

    @DeleteMapping("/reject")
    public ResponseEntity<?> rejectRequest(@Valid @RequestBody FriendRequestRequester requestBody) {
        var receiver = authUtils.getAuthenticatedUser();
        var requester = userService.findById(requestBody.getRequesterId());

        var request = requestService.findById(requester.getId(), receiver.getId());
        requestService.reject(request);

        return ResponseEntity.ok("Request rejected!");
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelRequest(@Valid @RequestBody FriendRequestReceiver requestId) {
        var user = authUtils.getAuthenticatedUser();
        var request = requestService.findById(user.getId(), requestId.getReceiverId());
        requestService.cancel(request);

        return ResponseEntity.ok("Request canceled!");
    }

}
