package org.kie.kogito.trusty.service.messaging.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

public class TraceOutputValueDTO {

    private final String id;
    private final String name;
    private final String status;
    private final TraceEventTypeDTO type;
    private final JsonNode value;
    @JsonInclude(NON_EMPTY)
    private final List<MessageDTO> messages;

    public TraceOutputValueDTO(String id, String name, String status, TraceEventTypeDTO type, JsonNode value, List<MessageDTO> messages) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.type = type;
        this.value = value;
        this.messages = messages;
    }
}
