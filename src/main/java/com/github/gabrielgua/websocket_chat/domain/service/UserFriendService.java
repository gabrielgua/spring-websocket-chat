package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFriendService {

    private final UserService userService;

    @Transactional(readOnly = true)
    public List<User> listFriendsById(Long id) {
        return userService.findById(id).getFriends().stream().toList();
    }
}
