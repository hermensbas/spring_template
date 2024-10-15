package nl.spring.template.project.app.template.controller;

import nl.spring.template.project.app.template.service.ExampleService;
import nl.spring.template.project.common.junit.annotation.UnitTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

@UnitTests
class ExampleControllerTest {

    @InjectMocks
    private ExampleController exampleController;

    @Mock
    private ExampleService exampleService;

    @Test
    void example1() {

        // Arrange
        var expected = "hi";
        Mockito
            .when(exampleService.getGreetingMessage())
            .thenCallRealMethod();

        // Act
        final var response = exampleController.getGreeting();

        // Assert
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void example2() {

        // Arrange
        var expected = "example";
        Mockito
            .when(exampleService.getGreetingMessage())
            .thenReturn(expected);

        // Act
        final var response = exampleController.getGreeting();

        // Assert
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void example3() {

        // Arrange
        var expected = "hi";
        Mockito
            .when(exampleService.getGreetingMessage())
            .thenThrow(RuntimeException.class);

        // Act
        final Executable response = () -> exampleController.getGreeting();

        // Assert
        assertThrows(RuntimeException.class, response);
    }
}
