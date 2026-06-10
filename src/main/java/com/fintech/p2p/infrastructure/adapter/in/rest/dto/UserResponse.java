package com.fintech.p2p.infrastructure.adapter.in.rest.dto;

import com.fintech.p2p.domain.model.User;

import java.math.BigDecimal;

/**
 * Outgoing response representing a user.
 *
 * Exposes only what the API wants to show, decoupled from the domain model.
 */
public record UserResponse(
        Long id,
        String name,
        String email,
        BigDecimal balance
) {
    // Factory method: build a response DTO from a domain User.
    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAccount().getBalance()
        );
    }
}