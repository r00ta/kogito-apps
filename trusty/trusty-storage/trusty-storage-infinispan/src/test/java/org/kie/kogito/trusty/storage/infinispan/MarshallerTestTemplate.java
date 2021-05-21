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
import java.util.HashMap;
import java.util.Map;

import org.infinispan.protostream.MessageMarshaller;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

abstract class MarshallerTestTemplate {

    protected MessageMarshaller.ProtoStreamWriter writer;
    protected MessageMarshaller.ProtoStreamReader reader;
    protected Map<String, String> storage;

    @BeforeEach
    protected void setup() throws IOException {
        this.storage = new HashMap<>();
        this.writer = mock(MessageMarshaller.ProtoStreamWriter.class);
        this.reader = mock(MessageMarshaller.ProtoStreamReader.class);
        doAnswer(invocationOnMock -> storage.put(invocationOnMock.getArgument(0), invocationOnMock.getArgument(1)))
                .when(writer)
                .writeString(any(String.class), any(String.class));
        doAnswer(invocationOnMock -> storage.get(invocationOnMock.getArgument(0)))
                .when(reader)
                .readString(any(String.class));
    }
}
