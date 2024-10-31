package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.service.UserFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/friends")
public class UserFriendController {

    private final UserMapper mapper;
    private final AuthUtils auth;
    private final UserFriendService service;

    @GetMapping
    public List<UserResponse> listFriends() {
        return mapper.toCollectionResponse(service.listFriendsById(auth.getAuthenticatedUser().getId()));
    }
}
