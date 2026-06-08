package com.fintech.p2p.domain.port.out;

import com.fintech.p2p.domain.model.Transaction;

import java.util.List;
import java.util.Optional;

/**
 * Output port for persisting and retrieving transactions.
 *
 * Defines what the domain needs regarding transaction storage,
 * without knowing the concrete technology (PostgreSQL, etc.).
 */
public interface TransactionRepository {

    /**
     * Persists a transaction (creates or updates) and returns the saved instance.
     */
    Transaction save(Transaction transaction);

    /**
     * Finds a transaction by its id.
     * @return an Optional with the transaction if found, or empty if not.
     */
    Optional<Transaction> findById(Long id);

    /**
     * Returns all transactions where the given user is the sender.
     * Used to build a user's transaction history.
     */
    List<Transaction> findBySenderId(Long senderId);
}