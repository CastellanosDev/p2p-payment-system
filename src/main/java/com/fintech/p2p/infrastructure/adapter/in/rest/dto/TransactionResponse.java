package com.fintech.p2p.infrastructure.adapter.in.rest.dto;

import com.fintech.p2p.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Outgoing response representing a completed transaction.
 */
public record TransactionResponse(
        Long id,
        Long senderId,
        Long receiverId,
        BigDecimal amount,
        String status,
        LocalDateTime createdAt
) {
    // Build a response DTO from a domain Transaction.
    public static TransactionResponse fromDomain(Transaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getSenderId(),
                tx.getReceiverId(),
                tx.getAmount(),
                tx.getStatus().name(),   // enum -> String
                tx.getCreatedAt()
        );
    }
}