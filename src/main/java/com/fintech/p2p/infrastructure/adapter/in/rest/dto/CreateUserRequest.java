package com.fintech.p2p.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * Incoming request body to create a user.
 *
 * Bean Validation annotations check the data before it ever reaches
 * the use case: name not blank, email well-formed, balance not negative.
 */
public record CreateUserRequest(

        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid")
        String email,

        @NotNull(message = "Initial balance is required")
        @PositiveOrZero(message = "Initial balance must be zero or positive")
        BigDecimal initialBalance
) {
}