package com.fintech.p2p.domain.exception;

/**
 * Thrown when a user attempts to transfer money to themselves.
 */
public class SameUserTransferException extends RuntimeException {
    public SameUserTransferException(String message) {
        super(message);
    }
}