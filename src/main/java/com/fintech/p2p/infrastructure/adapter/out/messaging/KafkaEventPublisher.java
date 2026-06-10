package com.fintech.p2p.infrastructure.adapter.out.messaging;

import com.fintech.p2p.domain.event.TransactionCompletedEvent;
import com.fintech.p2p.domain.port.out.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * EventPublisher adapter that publishes domain events to Kafka.
 *
 * Implements the output port using a KafkaTemplate. The domain stays
 * unaware of Kafka; only this adapter knows about it.
 */
@Component
public class KafkaEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventPublisher.class);
    private static final String TOPIC = "transfer.completed";

    private final KafkaTemplate<String, TransactionCompletedEvent> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, TransactionCompletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishTransactionCompleted(TransactionCompletedEvent event) {
        // The transaction id is used as the message key (guarantees ordering per transaction)
        kafkaTemplate.send(TOPIC, String.valueOf(event.transactionId()), event);
        log.info("Published to Kafka topic '{}': txId={}", TOPIC, event.transactionId());
    }
}