package org.kie.kogito.trusty.service.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class MessageDTO {

    private final LevelDTO level;
    private final MessageCategoryDTO category;
    private final String type;
    private final String sourceId;
    private final String text;
    private final MessageFEELEventDTO feelEvent;
    private final MessageExceptionFieldDTO exception;

    public MessageDTO(LevelDTO level, MessageCategoryDTO category, String type, String sourceId, String text, MessageFEELEventDTO feelEvent, MessageExceptionFieldDTO exception) {
        this.level = level;
        this.category = category;
        this.type = type;
        this.sourceId = sourceId;
        this.text = text;
        this.feelEvent = feelEvent;
        this.exception = exception;
    }

    public LevelDTO getLevel() {
        return level;
    }

    public MessageCategoryDTO getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getText() {
        return text;
    }

    public MessageFEELEventDTO getFeelEvent() {
        return feelEvent;
    }

    public MessageExceptionFieldDTO getException() {
        return exception;
    }
}