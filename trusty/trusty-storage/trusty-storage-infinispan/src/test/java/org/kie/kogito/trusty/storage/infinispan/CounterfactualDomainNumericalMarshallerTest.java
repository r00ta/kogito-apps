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

import java.util.List;

import org.infinispan.protostream.MessageMarshaller;
import org.kie.kogito.trusty.storage.api.model.CounterfactualDomainNumerical;
import org.kie.kogito.trusty.storage.infinispan.testfield.AbstractTestField;
import org.kie.kogito.trusty.storage.infinispan.testfield.DoubleTestField;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.kie.kogito.trusty.storage.api.model.CounterfactualDomainNumerical.LOWER_BOUND;
import static org.kie.kogito.trusty.storage.api.model.CounterfactualDomainNumerical.UPPER_BOUND;

public class CounterfactualDomainNumericalMarshallerTest extends MarshallerTestTemplate<CounterfactualDomainNumerical> {

    private static final List<AbstractTestField<CounterfactualDomainNumerical, ?>> TEST_FIELD_LIST = List.of(
            new DoubleTestField<>(LOWER_BOUND, 1.0, CounterfactualDomainNumerical::getLowerBound, CounterfactualDomainNumerical::setLowerBound),
            new DoubleTestField<>(UPPER_BOUND, 2.0, CounterfactualDomainNumerical::getUpperBound, CounterfactualDomainNumerical::setUpperBound));

    public CounterfactualDomainNumericalMarshallerTest() {
        super(CounterfactualDomainNumerical.class);
    }

    @Override
    protected CounterfactualDomainNumerical buildEmptyObject() {
        return new CounterfactualDomainNumerical();
    }

    @Override
    protected MessageMarshaller<CounterfactualDomainNumerical> buildMarshaller() {
        return new CounterfactualDomainNumericalMarshaller(new ObjectMapper());
    }

    @Override
    protected List<AbstractTestField<CounterfactualDomainNumerical, ?>> getTestFieldList() {
        return TEST_FIELD_LIST;
    }
}
