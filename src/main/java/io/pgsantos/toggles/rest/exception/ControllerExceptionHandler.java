package io.pgsantos.toggles.rest.exception;

import org.springframework.http.ResponseEntity;
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
}