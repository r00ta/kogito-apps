package org.kie.kogito.trusty.service.messaging.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class TraceResourceIdDTO {

    private final String modelNamespace;
    private final String modelName;
    @JsonInclude(NON_NULL)
    private final String decisionServiceId;
    @JsonInclude(NON_NULL)
    private final String decisionServiceName;

    public TraceResourceIdDTO(String modelNamespace, String modelName) {
        this(modelNamespace, modelName, null, null);
    }

    public TraceResourceIdDTO(String modelNamespace, String modelName, String decisionServiceId, String decisionServiceName) {
        this.modelNamespace = modelNamespace;
        this.modelName = modelName;
        this.decisionServiceId = decisionServiceId;
        this.decisionServiceName = decisionServiceName;
    }

    public String getModelNamespace() {
        return modelNamespace;
    }

    public String getModelName() {
        return modelName;
    }

    public String getDecisionServiceId() {
        return decisionServiceId;
    }

    public String getDecisionServiceName() {
        return decisionServiceName;
    }
}