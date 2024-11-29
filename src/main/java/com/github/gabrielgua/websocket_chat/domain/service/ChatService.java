package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.exception.ChatNotFoundException;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.repository.ChatRepository;
import com.github.gabrielgua.websocket_chat.infra.specs.ChatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository repository;

    @Transactional(readOnly = true)
    public List<Chat> findAll(ChatSpecification filter) {
        return repository.findAll(filter);
    }

    @Transactional(readOnly = true)
    public List<Chat> findAllByUser(User user) {
        return repository.findByUsersContaining(user);
    }

    @Transactional(readOnly = true)
    public Chat findById(String chatId) {
        return repository.findById(UUID.fromString(chatId)).orElseThrow(() -> new ChatNotFoundException(chatId));
    }

    @Transactional
    public Chat save(Chat chat) {
        return repository.save(chat);
    }

    @Transactional
    public void remove(Chat chat) {
        if (repository.existsById(chat.getId())) repository.delete(chat);
    }
}
