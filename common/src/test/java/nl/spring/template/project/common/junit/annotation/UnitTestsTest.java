package nl.spring.template.project.common.junit.annotation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@UnitTests
class UnitTestsTest {

    @Test
    void verifyClassAndTagAreEqual() {

        // Arrange
        final String className = UnitTests.class.getSimpleName();

        // Act
        final String tagName = Optional
            .ofNullable(UnitTests.class.getAnnotation(Tag.class))
            .map(Tag::value)
            .orElse(null);

        // Assert
        Assertions.assertNotNull(tagName, "@Tag not found");
        Assertions.assertEquals(className, tagName, "@Tag value does not match the class name");
    }
}
