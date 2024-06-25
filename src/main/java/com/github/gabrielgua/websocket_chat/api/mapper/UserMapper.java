package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.UserResponse;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .status(user.getStatus())
                .build();
    }

    public List<UserResponse> toCollectionResponse(List<User> users) {
        return users.stream().map(this::toResponse).toList();
    }
}
