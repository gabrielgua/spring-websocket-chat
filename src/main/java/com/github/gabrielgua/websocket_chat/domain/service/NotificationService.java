package com.github.gabrielgua.websocket_chat.domain.service;


import com.github.gabrielgua.websocket_chat.domain.model.Notification;
import com.github.gabrielgua.websocket_chat.domain.model.NotificationId;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    @Transactional(readOnly = true)
    public List<Notification> listByUser(User user) {
        return repository.findAllByUser(user);
    }

    @Transactional
    public Notification save(Notification notification) {
        return repository.save(notification);
    }

    @Transactional
    public void addCount(Notification notification) {
        notification.addToCount();
        repository.save(notification);
    }

    public void clearCount(Notification notification) {
        notification.clearCount();
        repository.save(notification);
    }
}
