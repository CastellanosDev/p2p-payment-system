package com.fintech.p2p.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain event published when a money transfer completes successfully.
 *
 * A record is used because an event is immutable data: once something
 * happened, it doesn't change. Records auto-generate constructor,
 * getters, equals, hashCode and toString.
 */
public record TransactionCompletedEvent(
        Long transactionId,
        Long senderId,
        Long receiverId,
        BigDecimal amount,
        LocalDateTime occurredAt
) {
}