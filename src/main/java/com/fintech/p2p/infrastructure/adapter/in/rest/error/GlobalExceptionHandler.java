package com.fintech.p2p.infrastructure.adapter.in.rest.error;

import com.fintech.p2p.domain.exception.InsufficientBalanceException;
import com.fintech.p2p.domain.exception.InvalidAmountException;
import com.fintech.p2p.domain.exception.SameUserTransferException;
import com.fintech.p2p.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Translates domain exceptions into proper HTTP responses,
 * centrally for the whole API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 NOT FOUND — the user does not exist
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFound(UserNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 422 UNPROCESSABLE ENTITY — not enough balance to complete the transfer
    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleInsufficientBalance(InsufficientBalanceException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    // 400 BAD REQUEST — invalid amount (zero or negative)
    @ExceptionHandler(InvalidAmountException.class)
    public ProblemDetail handleInvalidAmount(InvalidAmountException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 400 BAD REQUEST — sender and receiver are the same
    @ExceptionHandler(SameUserTransferException.class)
    public ProblemDetail handleSameUserTransfer(SameUserTransferException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}