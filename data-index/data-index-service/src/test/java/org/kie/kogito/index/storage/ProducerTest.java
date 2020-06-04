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

package org.kie.kogito.index.storage;

import javax.enterprise.inject.Instance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kie.kogito.index.IDataIndexStorageExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducerTest {

    @Mock
    Instance<IDataIndexStorageExtension> cacheServices;

    @InjectMocks
    Producer cacheProducer;

    @Mock
    Instance<IDataIndexStorageExtension> instance;

    @BeforeEach
    void prepare() {
        String storageType = "test";
        when(cacheServices.select(eq(new StorageImpl(storageType)))).thenReturn(instance);
        cacheProducer.storageType = storageType;
    }

    @Test
    void produceInfinispanCacheService() {
        cacheProducer.cacheService();

        verify(instance).get();
    }
}