package com.github.gabrielgua.websocket_chat.web.controller;

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

    @GetMapping("/users/connected")
    public List<User> listConnectedUsers() {
        return service.findAllConnected();
    }

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User connect(@Payload User user) {
        service.connect(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    public User disconnect(@Payload User user) {
        service.disconnect(user);
        return user;
    }
}
