package org.kie.kogito.trusty.service.messaging.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class TraceExecutionStepDTO {

    @JsonInclude(NON_NULL)
    private final TraceExecutionStepTypeDTO type;
    @JsonInclude(NON_DEFAULT)
    private final long duration;
    @JsonInclude(NON_NULL)
    private final String name;
    @JsonInclude(NON_NULL)
    private final Object result;
    @JsonInclude(NON_EMPTY)
    private final List<MessageDTO> messages;
    @JsonInclude(NON_EMPTY)
    private final Map<String, Object> additionalData;
    @JsonInclude(NON_EMPTY)
    private final List<TraceExecutionStepDTO> children;

    public TraceExecutionStepDTO(TraceExecutionStepTypeDTO type, long duration, String name, Object result, List<MessageDTO> messages, Map<String, Object> additionalData, List<TraceExecutionStepDTO> children) {
        this.type = type;
        this.duration = duration;
        this.name = name;
        this.result = result;
        this.messages = messages;
        this.additionalData = additionalData;
        this.children = children;
    }

    public TraceExecutionStepTypeDTO getType() {
        return type;
    }

    public long getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public Object getResult() {
        return result;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public Map<String, Object> getAdditionalData() {
        return additionalData;
    }

    public List<TraceExecutionStepDTO> getChildren() {
        return children;
    }
}