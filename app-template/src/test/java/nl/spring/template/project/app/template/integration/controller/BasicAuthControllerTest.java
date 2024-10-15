package nl.spring.template.project.app.template.integration.controller;

import io.restassured.RestAssured;
import nl.spring.template.project.app.template.Main;
import nl.spring.template.project.common.junit.annotation.IntegrationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import static nl.spring.template.project.app.template.integration.helper.RestAssuredHelper.loggedOnAs;
import static org.hamcrest.Matchers.containsString;

@IntegrationTests
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "management.server.port=",
    "spring.main.banner-mode=off",
    "spring.main.log-startup-info=off",
})
class BasicAuthControllerTest {

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
    void testUserAuthorizedUserEndpoint() {

        RestAssured
            .given(loggedOnAs("user", "user"))
            .when()
            .get("http://localhost/private/user/hi")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_PLAIN_VALUE)
            .body(containsString("Hello user"));
    }

    @Test
    void testAdminAuthorizedAdminEndpoint() {

        RestAssured
            .given(loggedOnAs("admin", "admin"))
            .when()
            .get("http://localhost/private/admin/hi")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_PLAIN_VALUE)
            .body(containsString("Hello admin"));
    }

    @Test
    void testAdminAuthorizedForUserEndpoint() {

        RestAssured
            .given(loggedOnAs("admin", "admin"))
            .when()
            .get("http://localhost/private/user/hi")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_PLAIN_VALUE)
            .body(containsString("Hello admin"));
    }

    @Test
    void testAnonymousAuthorizedForPublicEndpoint() {

        RestAssured
            .given()
            .when()
            .get("http://localhost/public/hi")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_PLAIN_VALUE)
            .body(containsString("Hello"));
    }

    @Test
    void testUserAuthorizedForPublicEndpoint() {

        RestAssured
            .given()
            .when()
            .get("http://localhost/public/hi")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_PLAIN_VALUE)
            .body(containsString("Hello"));
    }

    @Test
    void testAdminAuthorizedForPublicEndpoint() {

        RestAssured
            .given()
            .when()
            .get("http://localhost/public/hi")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.TEXT_PLAIN_VALUE)
            .body(containsString("Hello"));
    }

    @Test
    void testUserNotAuthorizedAdminEndpoint() {

        RestAssured
            .given(loggedOnAs("user", "user"))
            .when()
            .get("http://localhost/private/admin/hi")
            .then()
            .assertThat()
            .statusCode(HttpStatus.FORBIDDEN.value());
    }
}