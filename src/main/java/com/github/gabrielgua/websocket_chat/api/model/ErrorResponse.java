package com.github.gabrielgua.websocket_chat.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ErrorResponse {

    private int status;
    private String title;
    private String desc;
}
