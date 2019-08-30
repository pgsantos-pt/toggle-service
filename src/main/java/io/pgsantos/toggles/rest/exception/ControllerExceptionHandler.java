package io.pgsantos.toggles.rest.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException exception){
        return ResponseEntity.badRequest().body(Map.of("status", "FAIL", "error", exception.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException exception){
        return ResponseEntity.badRequest().body(Map.of("status", "FAIL", "error", exception.getMessage()));
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public final ResponseEntity<Map<String, String>> handleJpaObjectRetrievalFailureException(JpaObjectRetrievalFailureException exception){
        String errorMessage = StringUtils.isNotBlank(exception.getMessage()) ? exception.getMessage() : "";

        if(errorMessage.startsWith("Unable to find") && errorMessage.contains("Toggle")) {
            errorMessage = exception.getCause().getMessage();
            errorMessage = "Unable to find a toggle with id " + errorMessage.substring(errorMessage.length()-1);
        }
        return ResponseEntity.badRequest().body(Map.of("status", "FAIL", "error", errorMessage));
    }

    @ExceptionHandler(JpaSystemException.class)
    public final ResponseEntity<Map<String, String>> handleJpaSystemException(JpaSystemException exception) {
        return ResponseEntity.badRequest().body(Map.of("status", "FAIL", "error", exception.getCause().getMessage()));
    }
}