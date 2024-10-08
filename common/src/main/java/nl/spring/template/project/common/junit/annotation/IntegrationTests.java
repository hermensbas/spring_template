package nl.spring.template.project.common.junit.annotation;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Tag("IntegrationTests")
@Execution(CONCURRENT)
@TestMethodOrder(OrderAnnotation.class)
@Target({TYPE, METHOD})
@Retention(RUNTIME)
@ExtendWith(MockitoExtension.class)
public @interface IntegrationTests {

}

