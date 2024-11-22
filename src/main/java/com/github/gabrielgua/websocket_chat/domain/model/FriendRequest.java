package com.github.gabrielgua.websocket_chat.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "friend_requests")
public class FriendRequest {

    @EmbeddedId
    private FriendRequestId id;

    @ManyToOne
    @MapsId("requesterId")
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne
    @MapsId("receiverId")
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;
}
