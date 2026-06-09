package com.fintech.p2p.infrastructure.adapter.out.persistence;

import com.fintech.p2p.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for UserEntity.
 *
 * By extending JpaRepository, Spring auto-generates the implementation
 * with CRUD methods (save, findById, findAll, deleteById...) at runtime.
 * No code needed here.
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}