package com.github.gabrielgua.websocket_chat.domain.service;

import com.github.gabrielgua.websocket_chat.api.security.AuthUtils;
import com.github.gabrielgua.websocket_chat.domain.exception.*;
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
    private final AuthUtils authUtils;

    @Transactional
    public FriendRequest save(User requester, User receiver) {


        checkRequestDifferentUser(requester, receiver);
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

    @Transactional
    public void cancel(FriendRequest request) {
        checkUserCanCancelRequest(request);
        repository.delete(request);
    }

    public void checkUserCanCancelRequest(FriendRequest request) {
        var authenticated = authUtils.getAuthenticatedUser();
        if (!request.getRequester().equals(authenticated)) {
            throw new NotRequesterException();
        }
    }

    public void checkRequestDifferentUser(User requester, User receiver) {
        if (requester.equals(receiver)) {
            throw new EqualRequesterAndReceiverException();
        }
    }

    public void checkCanBeChanged(FriendRequest request) {
        if (request.getStatus() != PENDING) {
            throw new FriendRequestAlreadyResolvedException();
        }
    }

    public void checkAlreadyFriends(User requester, User receiver) {
        if (requester.getFriends().contains(receiver) || receiver.getFriends().contains(requester)) {
            throw new UserAlreadyFriendsException();
        }
    }

    @Transactional(readOnly = true)
    public void checkRequestAlreadyExists(User requester, User receiver) {

        var requestExists =
                repository.existsByRequesterAndReceiver(requester, receiver)
                || repository.existsByRequesterAndReceiver(receiver, requester);

        if (requestExists) {
            throw new FriendRequestAlreadyExistsException();
        }
    }

    @Transactional(readOnly = true)
    public FriendRequest findById(Long requesterId, Long receiverId) {
        var requestId = new FriendRequestId(requesterId, receiverId);
        return repository.findById(requestId).orElseThrow(() -> new FriendRequestNotFoundException(requestId));
    }
}
