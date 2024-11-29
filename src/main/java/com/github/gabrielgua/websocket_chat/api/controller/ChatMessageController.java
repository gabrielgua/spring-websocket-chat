package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.MessageMapper;
import com.github.gabrielgua.websocket_chat.api.model.MessageRequest;
import com.github.gabrielgua.websocket_chat.api.model.MessageResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.service.ChatService;
import com.github.gabrielgua.websocket_chat.domain.service.MessageService;
import com.github.gabrielgua.websocket_chat.domain.service.UserMessageService;
import com.github.gabrielgua.websocket_chat.web.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats/{chatId}/messages")
public class ChatMessageController {

    private final ChatService chatService;
    private final UserMessageService userMessageService;
    private final MessageService messageService;
    private final WebsocketService wsService;
    private final AuthUtils auth;
    private final MessageMapper messageMapper;

    @GetMapping
    public List<MessageResponse> findAllByChat(@PathVariable String chatId) {
        var chat = chatService.findById(chatId);
        return messageMapper.toCollectionResponse(messageService.findAllByChat(chat));
    }

    @PostMapping
    public void sendMessage(@PathVariable String chatId, @RequestBody MessageRequest request) {
        var sender = auth.getAuthenticatedUser();
        var chat = chatService.findById(request.getChatId());
        var message = messageService.save(messageMapper.toEntity(request, sender, chat));

        var response = messageMapper.toResponse(message);

        userMessageService.addUnreadToOffline(chat, message);
        wsService.sendMessageToChat(chatId, response);
    }

}

