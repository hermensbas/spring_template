package nl.spring.template.project.app.opera.config;

import io.restassured.RestAssured;
import nl.spring.template.project.app.opera.Main;
import nl.spring.template.project.common.junit.annotation.UnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.containsString;

@UnitTests
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "management.server.port=",
    "spring.main.banner-mode=off",
    "spring.main.log-startup-info=off",
    "application.servlet-filter.tracer.enabled=true"
})
class TracerCorrelationIdTest {

    @LocalServerPort
    private int port;

//    @MockBean
//    private ExampleProperties properties;

    @BeforeEach
    public void setUp() {
        RestAssured.reset();
        RestAssured.port = port;
    }

    @Test
    void testWithoutCorrelationIdHeader() {

        RestAssured
            .given()
            .when()
            .get("http://localhost/public/hi")
            .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testWithCorrelationIdHeader() {

        RestAssured
            .given()
            .header("X-Correlation-Id", "mySpecialHeaderValue")
            .when()
            .get("http://localhost/public/hi")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_PLAIN_VALUE)
            .body(containsString("Hello"));
    }
}