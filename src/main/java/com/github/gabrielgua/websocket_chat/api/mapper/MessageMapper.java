package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.MessageRequest;
import com.github.gabrielgua.websocket_chat.api.model.MessageResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.Chat;
import com.github.gabrielgua.websocket_chat.domain.model.Message;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageMapper {


    public MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .chat(message.getChat().getId().toString())
                .sender(message.getUser().getUsername())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }


    public MessageResponse toCompactResponse(Message message) {
        return MessageResponse.builder()
                .sender(message.getUser().getUsername())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }

    public Message toEntity(MessageRequest request, User sender, Chat chat) {
        return Message.builder()
                .chat(chat)
                .user(sender)
                .content(request.getContent())
                .build();
    }

    public List<MessageResponse> toCollectionResponse(List<Message> messages) {
        return messages.stream()
                .map(this::toResponse)
                .toList();
    }


}
