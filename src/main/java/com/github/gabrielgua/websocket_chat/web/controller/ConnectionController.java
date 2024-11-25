package com.github.gabrielgua.websocket_chat.web.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.UserConnectionRequest;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ConnectionController {

    private final UserService service;
    private final UserMapper mapper;



    @MessageMapping("user.connectUser")
    @SendTo("/topic/notifications")
    public UserResponse connect(@Payload UserConnectionRequest request) {
        return mapper.toResponse(service.connect(service.findById(request.getId())));
    }

    @MessageMapping("user.disconnectUser")
    @SendTo("/topic/notifications")
    public UserResponse disconnect(@Payload UserConnectionRequest request) {
        return mapper.toResponse(service.disconnect(service.findById(request.getId())));
    }
}
