package com.github.gabrielgua.websocket_chat.api.model;

import com.github.gabrielgua.websocket_chat.domain.model.ChatType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class ChatRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private ChatType type;

    @NotNull
    @Size(min = 2)
    private Set<Long> users;
}
