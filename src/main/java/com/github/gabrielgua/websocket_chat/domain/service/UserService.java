package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public List<User> findAllConnected() {
        return repository.findAllByStatus(UserStatus.ONLINE);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found with id"));
    }

    @Transactional
    public void save(User user) {
        repository.save(user);
    }

    @Transactional
    public User connect(User user) {
        user.setStatus(UserStatus.ONLINE);
        return repository.save(user);
    }

    @Transactional
    public User disconnect(User user) {
        user.setStatus(UserStatus.OFFLINE);
        return repository.save(user);
    }
}
