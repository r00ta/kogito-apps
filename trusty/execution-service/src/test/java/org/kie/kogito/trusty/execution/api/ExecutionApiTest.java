package org.kie.kogito.trusty.execution.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kie.kogito.trusty.execution.IExecutionService;
import org.kie.kogito.trusty.execution.models.Execution;
import org.kie.kogito.trusty.execution.responses.ExecutionTypeEnumResponse;
import org.kie.kogito.trusty.execution.responses.ExecutionsResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.isA;

@QuarkusTest
public class ExecutionApiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionApiTest.class);

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Inject
    IExecutionService executionService;

    @Test
    public void GivenNoExecutions_WhenExecutionEndpointIsCalled_ThenEmptyListIsReturned(){
        given().contentType(ContentType.JSON).when()
                .get("/executions?from=2000-01-01T00:00:00Z&to=2020-01-01T00:00:00Z")
                .then().statusCode(200).body("headers", isA(Collection.class));
    }


    @Test
    public void GivenARequestWithoutTimeRangeParameters_WhenExecutionEndpointIsCalled_ThenBadRequestIsReturned() {
        given().when().get("/executions").then().statusCode(400);
        given().when().get("/executions?from=2000-01-01T00:00:00Z").then().statusCode(400);
        given().when().get("/executions?to=2000-01-01T00:00:00Z").then().statusCode(400);
    }

    @Test
    public void GivenARequestWithMalformedTimeRange_WhenExecutionEndpointIsCalled_ThenBadRequestIsReturned() throws ParseException {
        Execution execution = new Execution("test1", sdf.parse("2020-01-01T00:00:00Z"), true, "name", "model", ExecutionTypeEnumResponse.DECISION );
        executionService.storeExecution("test1", execution);

        ExecutionsResponses response = given().contentType(ContentType.JSON).when().get("/executions?from=2000-01-01T00:00:00Z&to=2021-01-01T00:00:00Z").as(ExecutionsResponses.class);

        Assertions.assertEquals(1, response.getHeaders().size());
    }
}
