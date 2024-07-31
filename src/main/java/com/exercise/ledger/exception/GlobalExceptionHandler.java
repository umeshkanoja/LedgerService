package com.exercise.ledger.exception;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.exercise.ledger.exception.account.AccountsNotFoundException;
import com.exercise.ledger.exception.customer.CustomerNotFoundException;
import com.exercise.ledger.exception.customer.FieldAlreadyTakenException;
import com.exercise.ledger.exception.transaction.InsufficientBalanceException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @ExceptionHandler
        public ResponseEntity<ErrorResponse<Map.Entry<String, String>>> validationErrorsHandler(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest req) {

                List<Map.Entry<String, String>> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> Map.entry(error.getField(), error.getDefaultMessage()))
                                .collect(Collectors.toList());

                log.warn("Request validatin failed: {}", ex);
                return buildErrorResponse(HttpStatus.BAD_REQUEST, errors, req);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse<String>> invalidFormatExceptionHandler(
                        HttpMessageNotReadableException ex,
                        HttpServletRequest req) {

                log.warn("Invalid format: ", ex);
                return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()), req);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse<String>> insufficientBalanceExceptionHandler(
                        InsufficientBalanceException ex,
                        HttpServletRequest req) {

                log.warn("Insufficient balance: ", ex);
                return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()), req);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse<String>> customerNotFoundHandler(CustomerNotFoundException ex,
                        HttpServletRequest req) {

                log.warn("Customer not found exception", ex);
                return buildErrorResponse(HttpStatus.NOT_FOUND, List.of(ex.getMessage()), req);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse<String>> fieldAlreadyTakenExceptionHandler(FieldAlreadyTakenException ex,
                        HttpServletRequest req) {

                log.warn("Field already taken", ex);
                return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()), req);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse<String>> accountsNotFoundHandler(AccountsNotFoundException ex,
                        HttpServletRequest req) {

                // Sending 500 because our system is expected to have Accounts for every user,
                // and if we don't have any accounts, then our DB records are currupted for this
                // user.
                log.error("Accounts not found: ", ex);
                return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of(ex.getMessage()),
                                req);
        }

        @ExceptionHandler
        public ResponseEntity<ErrorResponse<String>> genericExceptionHandler(Exception ex,
                        HttpServletRequest req) {

                log.warn("Unhandled exception: ", ex);
                return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                List.of("Something went worng."), req);
        }

        private <T> ResponseEntity<ErrorResponse<T>> buildErrorResponse(HttpStatus status, List<T> errors,
                        HttpServletRequest req) {
                ErrorResponse<T> error = new ErrorResponse<>(
                                ZonedDateTime.now(),
                                status.value(),
                                req.getRequestURI(),
                                errors);

                return new ResponseEntity<>(error, status);
        }
}
