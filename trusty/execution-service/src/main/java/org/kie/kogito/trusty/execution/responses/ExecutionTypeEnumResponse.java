package org.kie.kogito.trusty.execution.responses;

public enum ExecutionTypeEnumResponse {
    DECISION("DECISION"),
    PROCESS("PROCESS");

    private String type;

    ExecutionTypeEnumResponse(String type){
        this.type = type;
    }
}
