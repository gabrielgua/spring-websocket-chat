package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.domain.model.FriendRequest;
import com.github.gabrielgua.websocket_chat.domain.model.FriendRequestId;
import com.github.gabrielgua.websocket_chat.domain.model.FriendRequestStatus;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import com.github.gabrielgua.websocket_chat.domain.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static com.github.gabrielgua.websocket_chat.domain.model.FriendRequestStatus.*;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository repository;
    private final UserService userService;

    @Transactional
    public FriendRequest save(User requester, User receiver) {

        checkRequestAlreadyExists(requester, receiver);
        checkAlreadyFriends(requester, receiver);

        var requestId = new FriendRequestId(requester.getId(), receiver.getId());
        var request = FriendRequest.builder()
                .id(requestId)
                .receiver(receiver)
                .requester(requester)
                .status(FriendRequestStatus.PENDING)
                .createdAt(OffsetDateTime.now())
                .build();

        return repository.save(request);
    }

    @Transactional
    public void accept(FriendRequest request) {
        checkCanBeChanged(request);
        request.setStatus(ACCEPTED);
        repository.save(request);
    }

    @Transactional
    public void reject(FriendRequest request) {
        checkCanBeChanged(request);
        request.setStatus(REJECTED);
        repository.save(request);
    }

    public void checkCanBeChanged(FriendRequest request) {
        if (request.getStatus() != PENDING) {
            throw new RuntimeException("Request is already " + request.getStatus());
        }
    }

    @Transactional(readOnly = true)
    public void checkAlreadyFriends(User requester, User receiver) {
        if (requester.getFriends().contains(receiver) || receiver.getFriends().contains(requester)) {
            throw new RuntimeException("Cannot make a request to an already friend user");
        }
    }

    @Transactional(readOnly = true)
    public void checkRequestAlreadyExists(User requester, User receiver) {
        if (repository.existsByRequesterAndReceiver(requester, receiver)) {
            throw new RuntimeException("Request already sent!");
        }

        if (repository.existsByRequesterAndReceiver(receiver, requester)) {
            throw new RuntimeException("This user has already sent you a request!");
        }
    }

    @Transactional(readOnly = true)
    public FriendRequest findByIds(Long requesterId, Long receiverId) {
        var requestId = new FriendRequestId(requesterId, receiverId);
        return repository.findById(requestId).orElseThrow(() -> new RuntimeException("Not found for ids!"));
    }
}
