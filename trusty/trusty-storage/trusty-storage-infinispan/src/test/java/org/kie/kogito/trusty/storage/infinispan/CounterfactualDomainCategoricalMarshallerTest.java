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

import java.util.Collections;
import java.util.List;

import org.infinispan.protostream.MessageMarshaller;
import org.kie.kogito.trusty.storage.api.model.CounterfactualDomainCategorical;
import org.kie.kogito.trusty.storage.infinispan.testfield.AbstractTestField;
import org.kie.kogito.trusty.storage.infinispan.testfield.CollectionTestField;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.kie.kogito.trusty.storage.api.model.CounterfactualDomainCategorical.CATEGORIES;

public class CounterfactualDomainCategoricalMarshallerTest extends MarshallerTestTemplate<CounterfactualDomainCategorical> {

    private static final List<AbstractTestField<CounterfactualDomainCategorical, ?>> TEST_FIELD_LIST = List.of(
            new CollectionTestField<>(CATEGORIES, Collections.emptyList(), CounterfactualDomainCategorical::getCategories, CounterfactualDomainCategorical::setCategories, JsonNode.class));

    public CounterfactualDomainCategoricalMarshallerTest() {
        super(CounterfactualDomainCategorical.class);
    }

    @Override
    protected CounterfactualDomainCategorical buildEmptyObject() {
        return new CounterfactualDomainCategorical();
    }

    @Override
    protected MessageMarshaller<CounterfactualDomainCategorical> buildMarshaller() {
        return new CounterfactualDomainCategoricalMarshaller(new ObjectMapper());
    }

    @Override
    protected List<AbstractTestField<CounterfactualDomainCategorical, ?>> getTestFieldList() {
        return TEST_FIELD_LIST;
    }
}
