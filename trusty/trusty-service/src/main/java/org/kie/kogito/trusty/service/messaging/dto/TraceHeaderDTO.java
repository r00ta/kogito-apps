package org.kie.kogito.trusty.service.messaging.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class TraceHeaderDTO {

    private final TraceEventTypeDTO type;
    private final String executionId;
    @JsonInclude(NON_NULL)
    private final Long startTimestamp;
    @JsonInclude(NON_NULL)
    private final Long endTimestamp;
    @JsonInclude(NON_NULL)
    private final Long duration;
    private final TraceResourceIdDTO resourceId;
    @JsonInclude(NON_EMPTY)
    private final List<MessageDTO> messages;

    public TraceHeaderDTO(TraceEventTypeDTO type, String executionId, Long startTs, Long endTs, Long duration, TraceResourceIdDTO resourceId, List<MessageDTO> messages) {
        this.type = type;
        this.executionId = executionId;
        this.startTimestamp = startTs == null || startTs <= 0 ? null : startTs;
        this.endTimestamp = endTs == null || endTs <= 0 ? null : endTs;
        this.duration = duration == null || duration <= 0 ? null : duration;
        this.resourceId = resourceId;
        this.messages = messages;
    }

    public TraceEventTypeDTO getType() {
        return type;
    }

    public String getExecutionId() {
        return executionId;
    }

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public Long getDuration() {
        return duration;
    }

    public TraceResourceIdDTO getResourceId() {
        return resourceId;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }
}