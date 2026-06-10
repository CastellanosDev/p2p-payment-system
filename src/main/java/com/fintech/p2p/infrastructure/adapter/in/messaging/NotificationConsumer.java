package com.fintech.p2p.infrastructure.adapter.in.messaging;

import com.fintech.p2p.domain.event.TransactionCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Input adapter that listens to transaction events from Kafka and reacts.
 *
 * Here it simulates sending notifications. It is fully decoupled from the
 * publisher: it only knows the topic, not who produces the events.
 */
@Component
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    @KafkaListener(topics = "transfer.completed", groupId = "notification-group")
    public void onTransactionCompleted(TransactionCompletedEvent event) {
        // Simulate notifying both users
        log.info("[NOTIFICATION] Transfer {} completed: {} sent {} to {}",
                event.transactionId(),
                event.senderId(),
                event.amount(),
                event.receiverId());
        log.info("  -> Notifying sender (id={})", event.senderId());
        log.info("  -> Notifying receiver (id={})", event.receiverId());
    }
}