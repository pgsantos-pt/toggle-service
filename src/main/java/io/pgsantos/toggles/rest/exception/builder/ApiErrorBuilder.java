package io.pgsantos.toggles.rest.exception.builder;

import io.pgsantos.toggles.rest.exception.ApiError;

import java.time.LocalDateTime;

public final class ApiErrorBuilder {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    private ApiErrorBuilder() {
    }

    public static ApiErrorBuilder anApiError() {
        return new ApiErrorBuilder();
    }

    public ApiErrorBuilder withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ApiErrorBuilder withStatus(int status) {
        this.status = status;
        return this;
    }

    public ApiErrorBuilder withError(String error) {
        this.error = error;
        return this;
    }

    public ApiErrorBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiError build() {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(timestamp);
        apiError.setStatus(status);
        apiError.setError(error);
        apiError.setMessage(message);
        return apiError;
    }
}
