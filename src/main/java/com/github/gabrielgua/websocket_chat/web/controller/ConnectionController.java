package com.github.gabrielgua.websocket_chat.web.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.UserConnectionRequest;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import com.github.gabrielgua.websocket_chat.web.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
@RequiredArgsConstructor
public class ConnectionController {

    private final UserService service;
    private final UserMapper mapper;
    private final WebsocketService wsService;
    
    @MessageMapping("user.connectUser")
    public void connect(@Payload UserConnectionRequest request) {
        var user = service.connect(service.findById(request.getId()));
        wsService.sendUserConnectionNotificationToFriends(user, mapper.toResponse(user));
        wsService.sendUserConnectionNotificationToChats(user, mapper.toResponse(user));
    }

    @MessageMapping("user.disconnectUser")
    public void disconnect(@Payload UserConnectionRequest request) {
        var user = service.disconnect(service.findById(request.getId()));
        wsService.sendUserConnectionNotificationToFriends(user, mapper.toResponse(user));
        wsService.sendUserConnectionNotificationToChats(user, mapper.toResponse(user));
    }
}