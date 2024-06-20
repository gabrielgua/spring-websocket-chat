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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConnectionController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("/users/connected")
    public List<User> listConnectedUsers() {
        return service.findAllConnected();
    }

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public UserResponse connect(@Payload UserConnectionRequest request) {
        var user = service.findById(request.getId());
        return mapper.toResponse(service.connect(user));
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public UserResponse disconnect(@Payload UserConnectionRequest request) {
        var user = service.findById(request.getId());
        return mapper.toResponse(service.disconnect(user));
    }
}
