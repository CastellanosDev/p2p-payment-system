package com.fintech.p2p.domain.exception;

/**
 * Thrown when an account does not have enough balance to complete a withdrawal.
 */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}