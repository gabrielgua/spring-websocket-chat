package com.github.gabrielgua.websocket_chat.domain.repository;

import com.github.gabrielgua.websocket_chat.domain.model.FriendRequest;
import com.github.gabrielgua.websocket_chat.domain.model.FriendRequestId;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, FriendRequestId> {

    boolean existsByRequesterAndReceiver(User requester, User receiver);

}
