/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.kogito.trusty.storage.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CounterfactualResult {

    public static final String EXECUTION_ID_FIELD = "executionId";
    public static final String COUNTERFACTUAL_ID_FIELD = "counterfactualId";
    public static final String COUNTERFACTUAL_SOLUTION_ID_FIELD = "solutionId";

    @JsonProperty(EXECUTION_ID_FIELD)
    private String executionId;

    @JsonProperty(COUNTERFACTUAL_ID_FIELD)
    private String counterfactualId;

    @JsonProperty(COUNTERFACTUAL_SOLUTION_ID_FIELD)
    private String solutionId;

    public CounterfactualResult() {
    }

    public CounterfactualResult(String executionId, String counterfactualId, String solutionId) {
        this.executionId = executionId;
        this.counterfactualId = counterfactualId;
        this.solutionId = solutionId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public String getCounterfactualId() {
        return counterfactualId;
    }

    public String getSolutionId() {
        return solutionId;
    }

    //-------------
    // Test methods
    //-------------

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public void setCounterfactualId(String counterfactualId) {
        this.counterfactualId = counterfactualId;
    }

    public void setSolutionId(String solutionId) {
        this.solutionId = solutionId;
    }
}
