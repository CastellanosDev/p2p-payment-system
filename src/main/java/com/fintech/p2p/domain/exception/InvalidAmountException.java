package com.fintech.p2p.domain.exception;

/**
 * Thrown when a monetary amount is invalid (zero, negative, or null).
 */
public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
}