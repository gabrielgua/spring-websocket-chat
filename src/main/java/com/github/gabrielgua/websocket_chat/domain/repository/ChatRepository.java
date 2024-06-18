package com.github.gabrielgua.websocket_chat.domain.repository;

import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
}
