package com.fintech.p2p.application.service;

import com.fintech.p2p.domain.model.Account;
import com.fintech.p2p.domain.model.User;
import com.fintech.p2p.domain.port.in.CreateUserUseCase;
import com.fintech.p2p.domain.port.out.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Application service implementing the create-user use case.
 *
 * Orchestrates the flow: builds the domain objects (User + Account)
 * and persists them through the UserRepository output port.
 */
@Service
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;

    // The repository is injected by Spring. The service depends on the
    // INTERFACE (port), not on any concrete database implementation.
    public CreateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name, String email, BigDecimal initialBalance) {
        // A new user starts with an account holding the initial balance.
        Account account = new Account(null, initialBalance);
        User user = new User(null, name, email, account);

        // Persist through the output port and return the saved user.
        return userRepository.save(user);
    }
}