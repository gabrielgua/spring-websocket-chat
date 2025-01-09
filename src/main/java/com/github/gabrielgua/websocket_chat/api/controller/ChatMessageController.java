package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.MessageMapper;
import com.github.gabrielgua.websocket_chat.api.mapper.PageMapper;
import com.github.gabrielgua.websocket_chat.api.model.MessageRequest;
import com.github.gabrielgua.websocket_chat.api.model.MessageResponse;
import com.github.gabrielgua.websocket_chat.api.model.PageResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.Message;
import com.github.gabrielgua.websocket_chat.domain.service.ChatService;
import com.github.gabrielgua.websocket_chat.domain.service.MessageService;
import com.github.gabrielgua.websocket_chat.domain.service.UserMessageService;
import com.github.gabrielgua.websocket_chat.web.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    private final PageMapper<Message, MessageResponse> pageMapper;

    @GetMapping
    public PageResponse<MessageResponse> findAllByChat(
            @PathVariable String chatId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var chat = chatService.findById(chatId);
        var pageable = messageService.findAllByChat(chat, page, size);

        return pageMapper.toPageResponse(pageable, messageMapper::toResponse);
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