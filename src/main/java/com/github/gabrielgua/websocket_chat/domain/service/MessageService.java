package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.exception.MessageNotFoundException;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.Message;
import com.github.gabrielgua.websocket_chat.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repository;

    @Transactional(readOnly = true)
    public List<Message> findAllByChat(Chat chat) {
        return repository.findAllByChat(chat);
    }

    @Transactional(readOnly = true)
    public Message findById(Long messageId) {
        return repository.findById(messageId).orElseThrow(() -> new MessageNotFoundException(messageId));
    }

    @Transactional
    public Message save(Message message) {
        return repository.save(message);
    }

}
