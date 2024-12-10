package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.exception.MessageNotFoundException;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.Message;
import com.github.gabrielgua.websocket_chat.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repository;

    @Transactional(readOnly = true)
    public Page<Message> findAllByChat(Chat chat, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return repository.findAllByChat(chat, pageable);
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