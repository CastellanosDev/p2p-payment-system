package com.fintech.p2p.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Incoming request body to transfer money between two users.
 */
public record TransferRequest(

        @NotNull(message = "Sender id is required")
        Long senderId,

        @NotNull(message = "Receiver id is required")
        Long receiverId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount
) {
}