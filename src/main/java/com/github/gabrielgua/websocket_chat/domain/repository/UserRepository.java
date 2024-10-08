package com.github.gabrielgua.websocket_chat.domain.repository;

import com.github.gabrielgua.websocket_chat.domain.model.UserStatus;
import com.github.gabrielgua.websocket_chat.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByStatus(UserStatus userStatus);

    Optional<User> findByUsername(String username);

    List<User> findByUsernameOrNameContainingIgnoreCase(String username, String name);

    boolean existsByUsername(String username);

}
