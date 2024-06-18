package com.github.gabrielgua.websocket_chat.domain.service;

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

    @Transactional
    public void save(User user) {
        repository.save(user);
    }


    @Transactional
    public void connect(User user) {
        var exists = repository.existsById(user.getId());

        if (exists) {
            user.setStatus(UserStatus.ONLINE);
            repository.save(user);
        }
    }

    @Transactional
    public void disconnect(User user) {
        var exists = repository.existsById(user.getId());

        if (exists) {
            user.setStatus(UserStatus.OFFLINE);
            repository.save(user);
        }
    }
}
