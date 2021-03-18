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
package org.kie.kogito.trusty.storage.infinispan;

import java.io.IOException;
import java.util.ArrayList;

import org.kie.kogito.trusty.storage.api.model.Counterfactual;
import org.kie.kogito.trusty.storage.api.model.Counterfactuals;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CounterfactualsMarshaller extends AbstractModelMarshaller<Counterfactuals> {

    public CounterfactualsMarshaller(ObjectMapper mapper) {
        super(mapper, Counterfactuals.class);
    }

    @Override
    public Counterfactuals readFrom(ProtoStreamReader reader) throws IOException {
        Counterfactuals counterfactuals = new Counterfactuals();
        counterfactuals.setCounterfactuals(reader.readCollection(Counterfactuals.COUNTERFACTUALS, new ArrayList<>(), Counterfactual.class));
        return counterfactuals;
    }

    @Override
    public void writeTo(ProtoStreamWriter writer, Counterfactuals input) throws IOException {
        writer.writeCollection(Counterfactuals.COUNTERFACTUALS, input.getCounterfactuals(), Counterfactual.class);
    }
}
