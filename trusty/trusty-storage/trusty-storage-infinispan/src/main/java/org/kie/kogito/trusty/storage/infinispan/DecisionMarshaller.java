/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
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

import org.kie.kogito.trusty.storage.api.model.Decision;
import org.kie.kogito.trusty.storage.api.model.Execution;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DecisionMarshaller extends AbstractModelMarshaller<Decision> {

    private final ObjectMapper mapper;

    public DecisionMarshaller(ObjectMapper mapper) {
        super(mapper, Decision.class);
        this.mapper = mapper;
    }

    @Override
    public Decision readFrom(ProtoStreamReader reader) throws IOException {
        return mapper.readValue(reader.readString(Constants.RAW_OBJECT_FIELD), Decision.class);
    }

    @Override
    public void writeTo(ProtoStreamWriter writer, Decision input) throws IOException {
        writer.writeString(Execution.EXECUTION_ID_FIELD, input.getExecutionId());
        writer.writeLong(Execution.EXECUTION_TIMESTAMP_FIELD, input.getExecutionTimestamp());
        writer.writeString(Constants.RAW_OBJECT_FIELD, mapper.writeValueAsString(input));
    }
}
