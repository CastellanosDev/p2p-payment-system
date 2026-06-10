package com.fintech.p2p.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

/**
 * Outgoing response with a user's balance.
 */
public record BalanceResponse(
        Long userId,
        BigDecimal balance
) {
}