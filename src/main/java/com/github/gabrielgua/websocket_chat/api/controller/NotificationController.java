package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.NotificationMapper;
import com.github.gabrielgua.websocket_chat.api.model.NotificationResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.Notification;
import com.github.gabrielgua.websocket_chat.domain.service.ChatService;
import com.github.gabrielgua.websocket_chat.domain.service.NotificationService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("notifications")
public class NotificationController {

    private final NotificationService service;
    private final AuthUtils authUtils;
    private final UserService userService;
    private final NotificationMapper mapper;

    @GetMapping
    public List<NotificationResponse> findAll() {
        var user = userService.findByUsername(authUtils.getAuthenticatedUsername());
        return mapper.toCollectionResponse(service.listByUser(user));
    }


}


