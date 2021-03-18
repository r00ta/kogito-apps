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
import java.util.HashSet;

import org.kie.kogito.trusty.storage.api.model.CounterfactualDomainCategorical;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CounterfactualDomainCategoricalMarshaller extends AbstractModelMarshaller<CounterfactualDomainCategorical> {

    public CounterfactualDomainCategoricalMarshaller(ObjectMapper mapper) {
        super(mapper, CounterfactualDomainCategorical.class);
    }

    @Override
    public CounterfactualDomainCategorical readFrom(ProtoStreamReader reader) throws IOException {
        return new CounterfactualDomainCategorical(
                reader.readCollection(CounterfactualDomainCategorical.CATEGORIES, new HashSet<>(), JsonNode.class));
    }

    @Override
    public void writeTo(ProtoStreamWriter writer, CounterfactualDomainCategorical input) throws IOException {
        writer.writeCollection(CounterfactualDomainCategorical.CATEGORIES, input.getCategories(), JsonNode.class);
    }
}
