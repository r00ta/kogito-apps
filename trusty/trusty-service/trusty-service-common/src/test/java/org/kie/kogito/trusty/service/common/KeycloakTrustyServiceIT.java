package org.kie.kogito.trusty.service.common;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.keycloak.representations.AccessTokenResponse;
import org.kie.kogito.testcontainers.KogitoKeycloakContainer;
import org.kie.kogito.testcontainers.quarkus.KeycloakQuarkusTestResource;
import org.kie.kogito.trusty.service.common.models.MatchedExecutionHeaders;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@QuarkusTest
@QuarkusTestResource(KeycloakQuarkusTestResource.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KeycloakTrustyServiceIT {

    private static final String VALID_USER = "jdoe";
    private static final String TRUSTY_ENDPOINT = "/executions";

    @ConfigProperty(name = KeycloakQuarkusTestResource.KOGITO_KEYCLOAK_PROPERTY)
    String keycloakURL;

    @InjectMock
    TrustyService trustyService;

    @Test
    void shouldReturnUnauthorized() {
        given().get(TRUSTY_ENDPOINT)
                .then().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void shouldReturnOkWhenValidUser() {
        when(trustyService.getExecutionHeaders(any(OffsetDateTime.class), any(OffsetDateTime.class), anyInt(), anyInt(), anyString())).thenReturn(new MatchedExecutionHeaders(new ArrayList<>(), 0));
        given().auth().oauth2(getAccessToken(VALID_USER)).get(TRUSTY_ENDPOINT)
                .then().statusCode(HttpStatus.SC_OK);
    }

    private String getAccessToken(String userName) {
        return given().param("grant_type", "password")
                .param("username", userName)
                .param("password", userName)
                .param("client_id", KogitoKeycloakContainer.CLIENT_ID)
                .param("client_secret", KogitoKeycloakContainer.CLIENT_SECRET)
                .when()
                .post(keycloakURL + "/protocol/openid-connect/token")
                .as(AccessTokenResponse.class).getToken();
    }
}