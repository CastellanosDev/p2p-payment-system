package com.fintech.p2p.infrastructure.adapter.out.messaging;

import com.fintech.p2p.domain.event.TransactionCompletedEvent;
import com.fintech.p2p.domain.port.out.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Temporary EventPublisher adapter that simply logs the event.
 *
 * It fulfils the EventPublisher port so the application can run, but does
 * not yet send anything to Kafka. The real Kafka implementation will
 * replace this in a later phase, without touching the domain or application.
 */
@Component
public class LoggingEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(LoggingEventPublisher.class);

    @Override
    public void publishTransactionCompleted(TransactionCompletedEvent event) {
        log.info("Event published -> TransactionCompleted: txId={}, from={}, to={}, amount={}",
                event.transactionId(),
                event.senderId(),
                event.receiverId(),
                event.amount());
    }
}