package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.exception.UserNotFoundException;
import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.github.gabrielgua.websocket_chat.domain.model.UserStatus.OFFLINE;
import static com.github.gabrielgua.websocket_chat.domain.model.UserStatus.ONLINE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    private static final String AVATAR_URL = "https://api.dicebear.com/9.x/thumbs/svg?radius=50";

    @Transactional(readOnly = true)
    public List<User> findAllConnected() {
        return repository.findAllByStatus(ONLINE);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<User> findByUsernameOrNameContaining(String term) {
        return repository.findByUsernameOrNameContainingIgnoreCase(term, term);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new RuntimeException("Username not found"));
    }


    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            user.setStatus(OFFLINE);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setAvatarUrl(String.format("%s&seed=%s", AVATAR_URL, user.getUsername() + UUID.randomUUID()));
        }

        return repository.save(user);
    }



    @Transactional
    public User connect(User user) {
        user.setStatus(ONLINE);
        return repository.save(user);
    }

    @Transactional
    public User disconnect(User user) {
        user.setStatus(OFFLINE);
        return repository.save(user);
    }
}
