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

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Counterfactuals {

    public static final String COUNTERFACTUALS = "counterfactuals";

    @JsonProperty(COUNTERFACTUALS)
    private Collection<Counterfactual> counterfactuals;

    public Counterfactuals() {
    }

    public Counterfactuals(Collection<Counterfactual> counterfactuals) {
        this.counterfactuals = counterfactuals;
    }

    public Collection<Counterfactual> getCounterfactuals() {
        return counterfactuals;
    }

    //-------------
    // Test methods
    //-------------

    public void setCounterfactuals(Collection<Counterfactual> counterfactuals) {
        this.counterfactuals = counterfactuals;
    }
}
