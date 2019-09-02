package io.pgsantos.toggles.rest.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());

        return ResponseEntity.badRequest().body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.BAD_REQUEST,
                        "error", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "message", errorMsg,
                        "path", headers.getLocation()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {

        return ResponseEntity.unprocessableEntity().body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.UNPROCESSABLE_ENTITY,
                        "error", HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                        "message", "It is not possible to process this request"));
    }

    @ExceptionHandler(PersistenceException.class)
    public final ResponseEntity<Object> handlePersistenceException(PersistenceException exception) {
        return ResponseEntity.unprocessableEntity().body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.UNPROCESSABLE_ENTITY,
                        "error", HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                        "message", "It is not possible to process this request"));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.NOT_FOUND,
                        "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "message", "The request produces in an empty result"));
    }
}