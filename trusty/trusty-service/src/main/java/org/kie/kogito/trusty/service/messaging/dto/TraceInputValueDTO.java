package org.kie.kogito.trusty.service.messaging.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class TraceInputValueDTO {

    private final String id;
    private final String name;
    private final TraceEventTypeDTO type;
    private final JsonNode value;
    @JsonInclude(NON_EMPTY)
    private final List<MessageDTO> messages;

    public TraceInputValueDTO(String id, String name, TraceEventTypeDTO type, JsonNode value, List<MessageDTO> messages) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TraceEventTypeDTO getType() {
        return type;
    }

    public JsonNode getValue() {
        return value;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }
}