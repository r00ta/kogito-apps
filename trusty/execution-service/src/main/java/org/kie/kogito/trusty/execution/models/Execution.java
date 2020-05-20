package org.kie.kogito.trusty.execution.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.kie.kogito.trusty.execution.responses.ExecutionTypeEnumResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Execution {

    @JsonProperty("executionId")
    private String executionId;

    @JsonProperty("executionDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date executionDate;

    @JsonProperty("executionSucceeded")
    private boolean executionSucceeded;

    @JsonProperty("executorName")
    private String executorName;

    @JsonProperty("executedModelName")
    private String executedModelName;

    @JsonProperty("executionType")
    private ExecutionTypeEnumResponse executionType;

    public Execution(){}

    public Execution(String executionId, Date executionDate, boolean executionSucceeded, String executorName, String executedModelName, ExecutionTypeEnumResponse executionType){
        this.executionId = executionId;
        this.executionDate = executionDate;
        this.executionSucceeded = executionSucceeded;
        this.executorName = executorName;
        this.executedModelName = executedModelName;
        this.executionType = executionType;
    }

    public String getExecutionId() {
        return executionId;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public boolean hasSucceeded() {
        return executionSucceeded;
    }

    public String getExecutorName() {
        return executorName;
    }

    public String getExecutedModelName() {
        return executedModelName;
    }

    public ExecutionTypeEnumResponse getExecutionType() {
        return executionType;
    }
}

