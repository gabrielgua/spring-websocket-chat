package com.github.gabrielgua.websocket_chat.domain.repository;

import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByStatus(UserStatus userStatus);
}
