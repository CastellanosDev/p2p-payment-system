package com.fintech.p2p.domain.model;

/**
 * Represents the lifecycle state of a money transfer.
 *
 * PENDING   - the transaction has been created but not yet processed.
 * COMPLETED - the money was successfully moved from sender to receiver.
 * FAILED    - the transaction could not be completed (e.g. insufficient funds).
 */
public enum TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED
}