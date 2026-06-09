package com.fintech.p2p.infrastructure.adapter.out.persistence;

import com.fintech.p2p.domain.model.User;
import com.fintech.p2p.domain.port.out.UserRepository;
import com.fintech.p2p.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.fintech.p2p.infrastructure.adapter.out.persistence.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Output adapter that implements the domain's UserRepository port
 * using Spring Data JPA under the hood.
 *
 * This is the bridge: the domain talks to the UserRepository interface,
 * and this class fulfils that contract by delegating to JPA and mapping
 * between domain models and JPA entities.
 */
@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        // Domain -> Entity, persist, then Entity -> Domain on the way back
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        // Find the entity, and if present, map it back to the domain model
        return jpaRepository.findById(id)
                .map(UserMapper::toDomain);
    }
}