package com.github.gabrielgua.websocket_chat.web.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.MessageMapper;
import com.github.gabrielgua.websocket_chat.api.model.MessageRequest;
import com.github.gabrielgua.websocket_chat.api.model.MessageResponse;
import com.github.gabrielgua.websocket_chat.domain.model.Message;
import com.github.gabrielgua.websocket_chat.domain.service.ChatService;
import com.github.gabrielgua.websocket_chat.domain.service.MessageService;
import com.github.gabrielgua.websocket_chat.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final ChatService chatService;
    private final UserService userService;
    private final MessageMapper mapper;

    @MessageMapping("/chats/{chatId}.sendMessage")
    public void processMessage(@DestinationVariable String chatId, @Payload MessageRequest request) {
        var chat = chatService.findById(request.getChatId());
        var sender = userService.findById(request.getSenderId());

        var message = mapper.toEntity(request, sender, chat);
        var response = mapper.toResponse(messageService.save(message));
        messagingTemplate.convertAndSend("/topic/chats/" + chatId, response);
    }

    @GetMapping("/chats/{chatId}/messages")
    public List<MessageResponse> findAllByChat(@PathVariable String chatId) {
        var chat = chatService.findById(chatId);
        return mapper.toCollectionResponse(messageService.findAllByChat(chat));
    }
}
