package com.fintech.p2p.infrastructure.adapter.out.persistence.mapper;

import com.fintech.p2p.domain.model.Account;
import com.fintech.p2p.domain.model.User;
import com.fintech.p2p.infrastructure.adapter.out.persistence.entity.UserEntity;

/**
 * Translates between the domain model (User) and the JPA entity (UserEntity).
 *
 * Keeps the two worlds decoupled: the domain never knows about JPA,
 * and persistence never imposes its constraints on the domain.
 */
public class UserMapper {

    /**
     * Domain -> Entity (used when saving).
     * The account's balance is flattened into the entity's balance column.
     */
    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAccount().getBalance()
        );
    }

    /**
     * Entity -> Domain (used when reading).
     * Rebuilds the Account from the stored balance and wraps it in a User.
     */
    public static User toDomain(UserEntity entity) {
        Account account = new Account(entity.getId(), entity.getBalance());
        return new User(entity.getId(), entity.getName(), entity.getEmail(), account);
    }
}