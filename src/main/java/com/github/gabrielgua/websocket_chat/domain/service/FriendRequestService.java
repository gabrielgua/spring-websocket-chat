package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.model.FriendRequest;
import com.github.gabrielgua.websocket_chat.domain.model.FriendRequestId;
import com.github.gabrielgua.websocket_chat.domain.model.FriendRequestStatus;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository repository;
    private final UserService userService;

    public void save(User requester, User receiver) {

        checkRequestAlreadyExists(requester, receiver);

        var requestId = new FriendRequestId(requester.getId(), receiver.getId());
        var request = FriendRequest.builder()
                .id(requestId)
                .receiver(receiver)
                .requester(requester)
                .status(FriendRequestStatus.PENDING)
                .createdAt(OffsetDateTime.now())
                .build();

        repository.save(request);
    }

    public void checkRequestAlreadyExists(User requester, User receiver) {
        if (repository.existsByRequesterAndReceiver(requester, receiver)) {
            throw new RuntimeException("Request already sent!");
        }

        if (repository.existsByRequesterAndReceiver(receiver, requester)) {
            throw new RuntimeException("This user has already sent you a request!");
        }
    }
}
