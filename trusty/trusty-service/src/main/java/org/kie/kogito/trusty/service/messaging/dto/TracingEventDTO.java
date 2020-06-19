/*
 *  Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.kie.kogito.trusty.service.messaging.dto;

import java.util.List;

public class TracingEventDTO {

    private final TraceHeaderDTO header;
    private final List<TraceInputValueDTO> inputs;
    private final List<TraceOutputValueDTO> outputs;
    private final List<TraceExecutionStepDTO> executionSteps;

    public TracingEventDTO(TraceHeaderDTO header, List<TraceInputValueDTO> inputs, List<TraceOutputValueDTO> outputs, List<TraceExecutionStepDTO> executionSteps) {
        this.header = header;
        this.inputs = inputs;
        this.outputs = outputs;
        this.executionSteps = executionSteps;
    }

    public TraceHeaderDTO getHeader() {
        return header;
    }

    public List<TraceInputValueDTO> getInputs() {
        return inputs;
    }

    public List<TraceOutputValueDTO> getOutputs() {
        return outputs;
    }

    public List<TraceExecutionStepDTO> getExecutionSteps() {
        return executionSteps;
    }
}
