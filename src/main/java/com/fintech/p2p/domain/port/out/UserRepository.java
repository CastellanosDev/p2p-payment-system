package com.fintech.p2p.domain.port.out;

import com.fintech.p2p.domain.model.User;

import java.util.Optional;

/**
 * Output port for persisting and retrieving users.
 *
 * The domain defines WHAT it needs (save a user, find one by id),
 * but not HOW. The concrete implementation (e.g. PostgreSQL via JPA)
 * lives in the infrastructure layer and adapts to this contract.
 */
public interface UserRepository {

    /**
     * Persists a user (creates or updates) and returns the saved instance.
     */
    User save(User user);

    /**
     * Finds a user by their id.
     * @return an Optional containing the user if found, or empty if not.
     */
    Optional<User> findById(Long id);
}