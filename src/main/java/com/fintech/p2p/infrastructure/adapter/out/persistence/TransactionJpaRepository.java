package com.fintech.p2p.infrastructure.adapter.out.persistence;

import com.fintech.p2p.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for TransactionEntity.
 */
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {

    // Spring auto-implements this from the method name: SELECT * WHERE sender_id = ?
    List<TransactionEntity> findBySenderId(Long senderId);
}