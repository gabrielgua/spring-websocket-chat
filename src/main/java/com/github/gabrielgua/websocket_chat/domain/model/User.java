package com.github.gabrielgua.websocket_chat.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_unread_messages",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private Set<Message> unread = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends = new HashSet<>();

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
    private Set<FriendRequest> sentRequests = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<FriendRequest> receivedRequests = new HashSet<>();

    public void addSentRequest(FriendRequest request) {
        sentRequests.add(request);
    }

    public void removeSentRequest(FriendRequest request) {
        sentRequests.remove(request);
    }

    public void addReceivedRequest(FriendRequest request) {
        receivedRequests.add(request);
    }

    public void removeReceivedRequest(FriendRequest request) {
        receivedRequests.add(request);
    }

    public void addFriend(User user) {
        this.friends.add(user);
    }

    public void removeFriend(User user) {
        this.friends.remove(user);
    }

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
