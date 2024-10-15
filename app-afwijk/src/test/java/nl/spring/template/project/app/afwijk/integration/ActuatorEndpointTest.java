package nl.spring.template.project.app.afwijk.integration;

import io.restassured.RestAssured;
import nl.spring.template.project.app.afwijk.Main;
import nl.spring.template.project.common.junit.annotation.IntegrationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpStatus.OK;

@IntegrationTests
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "management.server.port=",
    "spring.main.banner-mode=off",
    "spring.main.log-startup-info=off",
})
class ActuatorEndpointTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.reset();
        RestAssured.port = port;
    }

    @Test
    void testInfoEndpointIncludesGitAndBuildInformation() {

        RestAssured
            .given()
            .when()
            .get("http://localhost/actuator/info")
            .then()
            .assertThat()
            .statusCode(OK.value());
    }

    @Test
    void testHealthEndpoint() {

        RestAssured
            .given()
            .when()
            .get("http://localhost/actuator/health")
            .then()
            .assertThat()
            .statusCode(OK.value());
    }

    @Test
    void testMetricsEndpoint() {

        RestAssured
            .given()
            .when()
            .get("http://localhost/actuator/metrics")
            .then()
            .assertThat()
            .statusCode(OK.value());
    }

    @Test
    void testLoggingEndpoint() {

        RestAssured
            .given()
            .when()
            .get("http://localhost/actuator/logfile")
            .then()
            .assertThat()
            .statusCode(OK.value())
            .body(containsString(
                "################################# APPLICATION STARTED #################################"));
    }

}
