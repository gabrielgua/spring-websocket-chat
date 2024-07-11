package com.github.gabrielgua.websocket_chat.api.controller;

import com.github.gabrielgua.websocket_chat.api.mapper.MessageMapper;
import com.github.gabrielgua.websocket_chat.api.model.MessageResponse;
import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.model.Message;
import com.github.gabrielgua.websocket_chat.domain.service.MessageService;
import com.github.gabrielgua.websocket_chat.domain.service.UserMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/messages")
public class UserMessageController {

    private final AuthUtils authUtils;
    private final MessageMapper messageMapper;
    private final MessageService messageService;
    private final UserMessageService userMessageService;

    @GetMapping("/unread")
    public List<MessageResponse> listUnread() {
        var user = authUtils.getAuthenticatedUser();
        return messageMapper.toCollectionResponse(user.getUnread().stream().toList());
    }

    @PutMapping("/unread/add/{messageId}")
    public void addUnread(@PathVariable Long messageId) {
        var message = messageService.findById(messageId);

        var user = authUtils.getAuthenticatedUser();
        userMessageService.addMessageToUnreadList(user, message);
    }

    @DeleteMapping("/unread/remove")
    public void removeUnread(@RequestBody List<Long> messageIds) {
        var user = authUtils.getAuthenticatedUser();
        List<Message> messages = messageIds.stream().map(messageService::findById).toList();
        userMessageService.removeUnreadMessages(user, messages);
    }
}
