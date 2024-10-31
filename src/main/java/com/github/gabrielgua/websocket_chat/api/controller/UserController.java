package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.UserMapper;
import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping()
    public List<UserResponse> searchByNameOrUsername(@Param("term") String term) {
        return mapper.toCollectionResponse(service.findByUsernameOrNameContaining(term));
    }

}
