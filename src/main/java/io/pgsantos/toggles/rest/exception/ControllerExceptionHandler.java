package io.pgsantos.toggles.rest.exception;

import io.pgsantos.toggles.rest.exception.builder.ApiErrorBuilder;
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

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());

        return ResponseEntity.badRequest().body(
                ApiErrorBuilder.anApiError()
                        .withTimestamp(LocalDateTime.now())
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .withMessage(errorMsg)
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {

        return ResponseEntity.unprocessableEntity().body(
                ApiErrorBuilder.anApiError()
                        .withTimestamp(LocalDateTime.now())
                        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .withError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .withMessage("It is not possible to process this request")
                        .build());
    }

    @ExceptionHandler(PersistenceException.class)
    public final ResponseEntity<ApiError> handlePersistenceException(PersistenceException exception) {
        return ResponseEntity.unprocessableEntity().body(
                ApiErrorBuilder.anApiError()
                        .withTimestamp(LocalDateTime.now())
                        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .withError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .withMessage("It is not possible to process this request")
                        .build());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<ApiError> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiErrorBuilder.anApiError()
                        .withTimestamp(LocalDateTime.now())
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withError(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .withMessage("The request produces an empty result")
                        .build());
    }
}