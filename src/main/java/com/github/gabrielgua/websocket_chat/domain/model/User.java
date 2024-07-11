package com.github.gabrielgua.websocket_chat.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "users")
public class User {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_unread_messages",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private Set<Message> unread = new HashSet<>();


    public void addUnread(Message message) {
        this.unread.add(message);
    }

    private void removeUnread(Message message) {
        this.unread.remove(message);
    }

    public void removeUnreadList(List<Message> messages) {
        messages.forEach(this::removeUnread);
    }

    public boolean isNew() {
        return this.id == null;
    }

}
