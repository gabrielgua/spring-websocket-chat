package com.github.gabrielgua.websocket_chat.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity(name = "friend_requests")
public class FriendRequest {

    @EmbeddedId
    private FriendRequestId id;

    @MapsId("requesterId")
    @JoinColumn(name = "requester_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User requester;

    @MapsId("receiverId")
    @JoinColumn(name = "receiver_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;
}
