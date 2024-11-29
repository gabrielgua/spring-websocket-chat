package com.github.gabrielgua.websocket_chat.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private int status;
    private String error;
    private String message;
    private Map<String, Object> fields;
}
