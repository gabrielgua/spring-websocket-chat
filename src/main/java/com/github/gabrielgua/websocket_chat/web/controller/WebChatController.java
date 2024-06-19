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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final ChatService chatService;
    private final UserService userService;
    private final MessageMapper mapper;

    private static final String chatID = "ca3eb690-7afa-4a5d-b65f-e07cca178cf2";

    @MessageMapping("/chats/{chatId}")
    @SendTo("/topic/chat")
    public MessageResponse processMessage(@DestinationVariable String chatId, @Payload MessageRequest request) {
        var chat = chatService.findById(request.getChatId());
        var sender = userService.findById(request.getSenderId());

        var message = mapper.toEntity(request, sender, chat);

        return mapper.toResponse(messageService.save(message));
    }

    @GetMapping("/chats/{chatId}/messages")
    public List<Message> findAllByChat(@PathVariable String chatId) {
        var chat = chatService.findById(chatId);
        return messageService.findAllByChat(chat);
    }
}
