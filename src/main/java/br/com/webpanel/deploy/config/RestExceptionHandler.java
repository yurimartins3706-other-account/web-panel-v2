package br.com.webpanel.deploy.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * Global exception handler for REST controllers.
 */
@ControllerAdvice
public class RestExceptionHandler {
    /**
     * Handles ResponseStatusException and maps it to ErrorMessage response.
     *
     * @param ex the ResponseStatusException
     * @return ResponseEntity containing ErrorMessage and appropriate HTTP status
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorMessage> handleResponseStatusException(ResponseStatusException ex) {
        ErrorMessage errorMessage = new ErrorMessage(
            ex.getStatusCode().value(),            ex.getReason()
        );

        return new ResponseEntity<>(errorMessage, ex.getStatusCode());
    }
}