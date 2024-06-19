package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository repository;

    @Transactional
    public Chat save(Chat chat) {
        return repository.save(chat);
    }

    @Transactional(readOnly = true)
    public Chat findById(String uuid) {
        System.out.println(UUID.fromString(uuid));
        return repository.findById(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("Not found for uuid"));
    }

    @Transactional
    public void remove(Chat chat) {
        if (repository.existsById(chat.getId())) repository.delete(chat);
    }
}
