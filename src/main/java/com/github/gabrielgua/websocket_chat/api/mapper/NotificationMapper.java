package com.github.gabrielgua.websocket_chat.api.mapper;

import com.github.gabrielgua.websocket_chat.api.model.NotificationResponse;
import com.github.gabrielgua.websocket_chat.domain.model.Notification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .chatId(notification.getChat().getId().toString())
                .count(notification.getCount())
                .build();
    }

    public List<NotificationResponse> toCollectionResponse(List<Notification> notifications) {
        return notifications.stream()
                .map(this::toResponse)
                .toList();
    }
}
