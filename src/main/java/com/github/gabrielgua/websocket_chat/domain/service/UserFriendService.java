package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFriendService {

    private final UserService userService;
    private final AuthUtils auth;

    @Transactional(readOnly = true)
    public List<User> listFriendsById(Long id) {
        return userService.findById(id).getFriends().stream().toList();
    }

    public void addFriend(Long friendId) {
        var authenticatedId = auth.getAuthenticatedUser().getId();
        var authenticatedUser = userService.findById(authenticatedId);
        var friend = userService.findById(friendId);

        authenticatedUser.addFriend(friend);
        friend.addFriend(authenticatedUser);

        userService.save(authenticatedUser);
        userService.save(friend);
    }

    public void removeFriend(Long friendId) {
        var authenticatedId = auth.getAuthenticatedUser().getId();
        var authenticatedUser = userService.findById(authenticatedId);
        var friend = userService.findById(friendId);

        authenticatedUser.removeFriend(friend);
        friend.removeFriend(authenticatedUser);

        userService.save(authenticatedUser);
        userService.save(friend);
    }
}
