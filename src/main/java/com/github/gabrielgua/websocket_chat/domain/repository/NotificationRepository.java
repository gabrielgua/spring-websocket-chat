package com.github.gabrielgua.websocket_chat.domain.repository;

import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.Notification;
import com.github.gabrielgua.websocket_chat.domain.model.NotificationId;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUser(User user);
}
