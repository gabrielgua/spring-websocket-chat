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

    private static final String IMAGE_URL = "https://api.dicebear.com/9.x/initials/svg?backgroundType=gradientLinear&fontWeight=700";
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
        if (chat.isNew() && chat.isGroup()) {
            chat.setId(UUID.randomUUID());
            var chatName = chat.getName().trim().replace(" ", "_");
            var imageUrl = String.format("%s&seed=%s", IMAGE_URL, chatName);
            chat.setImageUrl(imageUrl);
        }

        return repository.save(chat);
    }

    @Transactional
    public void remove(Chat chat) {
        if (repository.existsById(chat.getId())) repository.delete(chat);
    }
}