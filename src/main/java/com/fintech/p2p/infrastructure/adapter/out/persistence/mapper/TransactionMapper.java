package com.fintech.p2p.infrastructure.adapter.out.persistence.mapper;

import com.fintech.p2p.domain.model.Transaction;
import com.fintech.p2p.infrastructure.adapter.out.persistence.entity.TransactionEntity;

/**
 * Translates between the domain model (Transaction) and the JPA entity.
 */
public class TransactionMapper {

    public static TransactionEntity toEntity(Transaction tx) {
        return new TransactionEntity(
                tx.getId(),
                tx.getSenderId(),
                tx.getReceiverId(),
                tx.getAmount(),
                tx.getStatus(),
                tx.getCreatedAt()
        );
    }

    public static Transaction toDomain(TransactionEntity entity) {
        // Rebuild the domain transaction from stored data.
        Transaction tx = new Transaction(entity.getSenderId(), entity.getReceiverId(), entity.getAmount());
        // The constructor sets status PENDING and a new timestamp, so we restore
        // the persisted status here. (See note below.)
        if (entity.getStatus() == com.fintech.p2p.domain.model.TransactionStatus.COMPLETED) {
            tx.markCompleted();
        } else if (entity.getStatus() == com.fintech.p2p.domain.model.TransactionStatus.FAILED) {
            tx.markFailed();
        }
        return tx;
    }
}