package com.github.gabrielgua.websocket_chat.domain.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.usertype.UserTypeSupport;

import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chats")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Chat {

    @Id
    @EqualsAndHashCode.Include
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    private String name;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "chats_users",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ChatType type;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    public boolean isPrivate() {
        return this.type == ChatType.PRIVATE;
    }

    public boolean isGroup() {
        return this.type == ChatType.GROUP;
    }
}
