package com.exercise.ledger.exception;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorResponseUtils {
    public static <T> ResponseEntity<ErrorResponse<T>> buildErrorResponse(HttpStatus status, List<T> errors,
            HttpServletRequest req) {
        ErrorResponse<T> error = new ErrorResponse<>(
                ZonedDateTime.now(),
                status.value(),
                req.getRequestURI(),
                errors);

        return new ResponseEntity<>(error, status);
    }
}
