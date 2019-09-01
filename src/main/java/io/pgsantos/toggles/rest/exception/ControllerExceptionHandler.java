package io.pgsantos.toggles.rest.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException exception){
        return ResponseEntity.badRequest().body(Map.of("status", "FAIL", "error", exception.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        return ResponseEntity.unprocessableEntity().body(Map.of("status", "FAIL", "error", "It is not possible to process this request"));
    }

    @ExceptionHandler(PersistenceException.class)
    public final ResponseEntity<Map<String, String>> handlePersistenceException(PersistenceException exception){
        return ResponseEntity.unprocessableEntity().body(Map.of("status", "FAIL", "error", "It is not possible to process this request"));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<Void> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception){
        return ResponseEntity.notFound().build();
    }
}