package org.kie.kogito.trusty.service.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.kie.dmn.api.feel.runtime.events.FEELEvent;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class MessageFEELEventDTO {

    private final FEELEvent.Severity severity;
    private final String message;
    @JsonInclude(NON_NULL)
    private final Integer line;
    @JsonInclude(NON_NULL)
    private final Integer column;
    @JsonInclude(NON_NULL)
    private final MessageExceptionFieldDTO sourceException;

    public MessageFEELEventDTO(FEELEvent.Severity severity, String message, Integer line, Integer column, MessageExceptionFieldDTO sourceException) {
        this.severity = severity;
        this.message = message;
        this.line = line == null || line < 0 ? null : line;
        this.column = column == null || column < 0 ? null : column;
        this.sourceException = sourceException;
    }

    public FEELEvent.Severity getSeverity() {
        return severity;
    }

    public String getMessage() {
        return message;
    }

    public Integer getLine() {
        return line;
    }

    public Integer getColumn() {
        return column;
    }

    public MessageExceptionFieldDTO getSourceException() {
        return sourceException;
    }
}
