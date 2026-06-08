package com.fintech.p2p.domain.port.out;

import com.fintech.p2p.domain.event.TransactionCompletedEvent;

/**
 * Output port for publishing domain events.
 *
 * The domain declares that it needs to publish events when relevant
 * things happen, without knowing the underlying technology (Kafka, etc.).
 */
public interface EventPublisher {

    /**
     * Publishes an event signalling that a transfer completed successfully.
     */
    void publishTransactionCompleted(TransactionCompletedEvent event);
}