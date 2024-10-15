package nl.spring.template.project.app.opera.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Globally maps internal exceptions to rest responses. Also prevent the default spring mechanism
 * invoking the /error redirect.
 */
@ControllerAdvice
public class ExceptionMapConfig {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException exception) {

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<Object> handleNoHandlerFoundExceptionException(final NoHandlerFoundException exception) {

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(final RuntimeException exception) {

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}