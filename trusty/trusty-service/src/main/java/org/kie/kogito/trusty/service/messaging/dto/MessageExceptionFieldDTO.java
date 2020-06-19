package org.kie.kogito.trusty.service.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class MessageExceptionFieldDTO {

    private final String className;
    private final String message;
    @JsonInclude(NON_NULL)
    private final MessageExceptionFieldDTO cause;

    public MessageExceptionFieldDTO(String className, String message, MessageExceptionFieldDTO cause) {
        this.className = className;
        this.message = message;
        this.cause = cause;
    }

    public String getClassName() {
        return className;
    }

    public String getMessage() {
        return message;
    }

    public MessageExceptionFieldDTO getCause() {
        return cause;
    }
}