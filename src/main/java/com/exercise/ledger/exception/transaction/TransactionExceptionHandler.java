package com.exercise.ledger.exception.transaction;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.exercise.ledger.exception.ErrorResponse;
import com.exercise.ledger.exception.ErrorResponseUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class TransactionExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse<String>> insufficientBalanceExceptionHandler(InsufficientBalanceException ex,
            HttpServletRequest req) {

        log.warn("Insufficient balance: ", ex);
        return ErrorResponseUtils.buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()), req);
    }
}
