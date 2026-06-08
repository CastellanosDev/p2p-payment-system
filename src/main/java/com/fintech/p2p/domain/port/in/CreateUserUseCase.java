package com.fintech.p2p.domain.port.in;

import com.fintech.p2p.domain.model.User;

import java.math.BigDecimal;

/**
 * Input port for creating a new user.
 *
 * Defines WHAT the application offers (create a user with an initial balance),
 * leaving the HOW to its implementation in the application layer.
 */
public interface CreateUserUseCase {

    /**
     * Creates a new user with an account holding the given initial balance.
     * @return the created user.
     */
    User createUser(String name, String email, BigDecimal initialBalance);
}