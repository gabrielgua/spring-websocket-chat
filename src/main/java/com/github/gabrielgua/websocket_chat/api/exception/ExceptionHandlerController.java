package com.github.gabrielgua.websocket_chat.api.exception;

import com.github.gabrielgua.websocket_chat.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Something went wrong. Try again later!";

    private Problem.ProblemBuilder createProblemBuilder(String message, HttpStatus status) {
        return Problem
                .builder()
                .error(status.name())
                .status(status.value())
                .message(message);
    }

    private Problem.ProblemBuilder createProblemBuilder(String message, String error, HttpStatus status) {
        return Problem
                .builder()
                .error(error)
                .status(status.value())
                .message(message);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (body == null || body instanceof String) {
            var status = HttpStatus.valueOf(statusCode.value());
            body = createProblemBuilder(!ex.getMessage().isEmpty() ? ex.getMessage() : GENERIC_ERROR_MESSAGE, status).build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    public ResponseEntity<?> handleNotFound(NotFoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problem = createProblemBuilder(ex.getMessage(), "RESOURCE_NOT_FOUND", status)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(ex, null, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        return handleNotFound(ex, request);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<?> handleChatNotFound(ChatNotFoundException ex, WebRequest request) {
        return handleNotFound(ex, request);
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<?> handleMessageNotFound(MessageNotFoundException ex, WebRequest request) {
        return handleNotFound(ex, request);
    }

    @ExceptionHandler(FriendRequestNotFoundException.class)
    public ResponseEntity<?> handleFriendRequestNotFound(FriendRequestNotFoundException ex, WebRequest request) {
        return handleNotFound(ex, request);
    }

    @ExceptionHandler(EqualRequesterAndReceiverException.class)
    public ResponseEntity<?> handleEqualRequesterAndReceiver(EqualRequesterAndReceiverException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problem = createProblemBuilder(ex.getMessage(), "SAME_REQUESTER_AND_RECEIVER", status)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(FriendRequestAlreadyExistsException.class)
    public ResponseEntity<?> handleFriendRequestAlreadyExists(FriendRequestAlreadyExistsException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problem = createProblemBuilder(ex.getMessage(), "FRIEND_REQUEST_ALREADY_EXISTS", status)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserAlreadyFriendsException.class)
    public ResponseEntity<?> handleUserAlreadyFriends(UserAlreadyFriendsException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problem = createProblemBuilder(ex.getMessage(), "USERS_ALREADY_FRIENDS", status)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NotRequesterException.class)
    public ResponseEntity<?> handleNotRequester(NotRequesterException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problem = createProblemBuilder(ex.getMessage(), "NOT_REQUESTER", status)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(FriendRequestAlreadyResolvedException.class)
    public ResponseEntity<?> handleFriendRequestAlreadyResolved(FriendRequestAlreadyResolvedException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var problem = createProblemBuilder(ex.getMessage(), "REQUEST_ALREADY_RESOLVED", status)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
}
