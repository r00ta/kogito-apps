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

package org.kie.kogito.trusty.service.common;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.kogito.trusty.service.common.messaging.incoming.ModelIdentifier;
import org.kie.kogito.trusty.service.common.models.MatchedExecutionHeaders;
import org.kie.kogito.trusty.storage.api.model.Counterfactual;
import org.kie.kogito.trusty.storage.api.model.DMNModelWithMetadata;
import org.kie.kogito.trusty.storage.api.model.Decision;
import org.kie.kogito.trusty.storage.common.TrustyStorageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractTrustyServiceIT {

    @Inject
    TrustyService trustyService;

    @Inject
    TrustyStorageService trustyStorageService;

    @BeforeEach
    public void setup() {
        trustyStorageService.getCounterfactualStorage().clear();
        trustyStorageService.getCounterfactualResultStorage().clear();
        trustyStorageService.getExplainabilityResultStorage().clear();
        trustyStorageService.getDecisionsStorage().clear();
        trustyStorageService.getModelStorage().clear();
    }

    @Test
    public void testStoreAndRetrieveExecution() {
        storeExecution("myExecution", 1591692958000L);

        OffsetDateTime from = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1591692957000L), ZoneOffset.UTC);
        OffsetDateTime to = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1591692959000L), ZoneOffset.UTC);
        MatchedExecutionHeaders result = trustyService.getExecutionHeaders(from, to, 100, 0, "");
        Assertions.assertEquals(1, result.getExecutions().size());
        Assertions.assertEquals("myExecution", result.getExecutions().get(0).getExecutionId());
    }

    @Test
    public void givenTwoExecutionsWhenTheQueryExcludesOneExecutionThenOnlyOneExecutionIsReturned() {
        storeExecution("myExecution", 1591692950000L);
        storeExecution("executionId2", 1591692958000L);

        OffsetDateTime from = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1591692940000L), ZoneOffset.UTC);
        OffsetDateTime to = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1591692955000L), ZoneOffset.UTC);
        MatchedExecutionHeaders result = trustyService.getExecutionHeaders(from, to, 100, 0, "");
        Assertions.assertEquals(1, result.getExecutions().size());
        Assertions.assertEquals("myExecution", result.getExecutions().get(0).getExecutionId());
    }

    @Test
    public void givenTwoExecutionsWhenThePrefixIsUsedThenOnlyOneExecutionIsReturned() {
        storeExecution("myExecution", 1591692950000L);
        storeExecution("executionId2", 1591692958000L);

        OffsetDateTime from = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1591692940000L), ZoneOffset.UTC);
        OffsetDateTime to = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1591692959000L), ZoneOffset.UTC);
        MatchedExecutionHeaders result = trustyService.getExecutionHeaders(from, to, 100, 0, "my");
        Assertions.assertEquals(1, result.getExecutions().size());
        Assertions.assertEquals("myExecution", result.getExecutions().get(0).getExecutionId());
    }

    @Test
    public void givenAnExecutionWhenGetDecisionByIdIsCalledThenTheExecutionIsReturned() {
        String executionId = "myExecution";
        storeExecution(executionId, 1591692950000L);

        Decision result = trustyService.getDecisionById(executionId);
        Assertions.assertEquals(executionId, result.getExecutionId());
    }

    @Test
    public void givenADuplicatedDecisionWhenTheDecisionIsStoredThenAnExceptionIsRaised() {
        String executionId = "myExecution";
        storeExecution(executionId, 1591692950000L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> storeExecution(executionId, 1591692950000L));
    }

    @Test
    public void givenNoExecutionsWhenADecisionIsRetrievedThenAnExceptionIsRaised() {
        String executionId = "myExecution";
        Assertions.assertThrows(IllegalArgumentException.class, () -> trustyService.getDecisionById(executionId));
    }

    @Test
    public void givenAModelWhenGetModelByIdIsCalledThenTheModelIsReturned() {
        String model = "definition";
        String modelId = "name:namespace";
        storeModel(model);

        DMNModelWithMetadata result = getModel();
        Assertions.assertEquals(model, result.getModel());
    }

    @Test
    public void givenADuplicatedModelWhenTheModelIsStoredThenAnExceptionIsRaised() {
        String model = "definition";
        storeModel(model);
        Assertions.assertThrows(IllegalArgumentException.class, () -> storeModel(model));
    }

    @Test
    public void givenNoModelsWhenAModelIsRetrievedThenAnExceptionIsRaised() {
        Assertions.assertThrows(IllegalArgumentException.class, this::getModel);
    }

    @Test
    public void testStoreSingleAndRetrieveSingleCounterfactualsWithEmptyDefinition() {
        String executionId = "myCFExecution1";
        storeExecution(executionId, 0L);

        Counterfactual request = trustyService.requestCounterfactuals(executionId, Collections.emptyList(), Collections.emptyList());

        assertNotNull(request);
        assertEquals(request.getExecutionId(), executionId);
        assertNotNull(request.getCounterfactualId());

        Counterfactual result = trustyService.getCounterfactual(executionId, request.getCounterfactualId());
        assertNotNull(result);
        assertEquals(request.getExecutionId(), result.getExecutionId());
        assertEquals(request.getCounterfactualId(), result.getCounterfactualId());
    }

    @Test
    public void testStoreMultipleAndRetrieveAllCounterfactualsWithEmptyDefinition() {
        String executionId = "myCFExecution2";
        storeExecution(executionId, 0L);

        Counterfactual request1 = trustyService.requestCounterfactuals(executionId, Collections.emptyList(), Collections.emptyList());
        Counterfactual request2 = trustyService.requestCounterfactuals(executionId, Collections.emptyList(), Collections.emptyList());

        List<Counterfactual> result = trustyService.getCounterfactuals(executionId);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getCounterfactualId().equals(request1.getCounterfactualId())));
        assertTrue(result.stream().anyMatch(c -> c.getCounterfactualId().equals(request2.getCounterfactualId())));
    }

    @Test
    public void testStoreMultipleAndRetrieveSingleCounterfactualWithEmptyDefinition() {
        String executionId = "myCFExecution3";
        storeExecution(executionId, 0L);

        Counterfactual request1 = trustyService.requestCounterfactuals(executionId, Collections.emptyList(), Collections.emptyList());
        Counterfactual request2 = trustyService.requestCounterfactuals(executionId, Collections.emptyList(), Collections.emptyList());

        Counterfactual result1 = trustyService.getCounterfactual(executionId, request1.getCounterfactualId());
        assertNotNull(result1);
        assertEquals(request1.getCounterfactualId(), result1.getCounterfactualId());

        Counterfactual result2 = trustyService.getCounterfactual(executionId, request2.getCounterfactualId());
        assertNotNull(result2);
        assertEquals(request2.getCounterfactualId(), result2.getCounterfactualId());
    }

    @Test
    public void testStoreMultipleForMultipleExecutionsAndRetrieveSingleCounterfactualWithEmptyDefinition() {
        String executionId1 = "myCFExecution1";
        storeExecution(executionId1, 0L);

        String executionId2 = "myCFExecution2";
        storeExecution(executionId2, 0L);

        Counterfactual request1 = trustyService.requestCounterfactuals(executionId1, Collections.emptyList(), Collections.emptyList());
        assertNotNull(request1);
        assertEquals(request1.getExecutionId(), executionId1);
        assertNotNull(request1.getCounterfactualId());

        Counterfactual request2 = trustyService.requestCounterfactuals(executionId2, Collections.emptyList(), Collections.emptyList());
        assertNotNull(request2);
        assertEquals(request2.getExecutionId(), executionId2);
        assertNotNull(request2.getCounterfactualId());

        Counterfactual result1 = trustyService.getCounterfactual(executionId1, request1.getCounterfactualId());
        assertNotNull(result1);
        assertEquals(request1.getExecutionId(), result1.getExecutionId());
        assertEquals(request1.getCounterfactualId(), result1.getCounterfactualId());
    }

    private Decision storeExecution(String executionId, Long timestamp) {
        Decision decision = new Decision();
        decision.setExecutionId(executionId);
        decision.setExecutionTimestamp(timestamp);
        trustyService.storeDecision(decision.getExecutionId(), decision);
        return decision;
    }

    private DMNModelWithMetadata storeModel(String model) {
        DMNModelWithMetadata dmnModelWithMetadata = new DMNModelWithMetadata("groupId", "artifactId", "modelVersion", "dmnVersion", "name", "namespace", model);
        ModelIdentifier identifier = new ModelIdentifier("groupId",
                "artifactId",
                "version",
                "name",
                "namespace");
        trustyService.storeModel(identifier, dmnModelWithMetadata);
        return dmnModelWithMetadata;
    }

    private DMNModelWithMetadata getModel() {
        ModelIdentifier identifier = new ModelIdentifier("groupId",
                "artifactId",
                "version",
                "name",
                "namespace");
        return trustyService.getModelById(identifier);
    }

}
