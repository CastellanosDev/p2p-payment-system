package com.fintech.p2p.infrastructure.adapter.out.persistence;

import com.fintech.p2p.domain.model.Transaction;
import com.fintech.p2p.domain.port.out.TransactionRepository;
import com.fintech.p2p.infrastructure.adapter.out.persistence.entity.TransactionEntity;
import com.fintech.p2p.infrastructure.adapter.out.persistence.mapper.TransactionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Output adapter implementing the TransactionRepository port using Spring Data JPA.
 */
@Component
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;

    public TransactionRepositoryAdapter(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = TransactionMapper.toEntity(transaction);
        TransactionEntity saved = jpaRepository.save(entity);
        return TransactionMapper.toDomain(saved);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return jpaRepository.findById(id)
                .map(TransactionMapper::toDomain);
    }

    @Override
    public List<Transaction> findBySenderId(Long senderId) {
        return jpaRepository.findBySenderId(senderId)
                .stream()
                .map(TransactionMapper::toDomain)
                .toList();
    }
}