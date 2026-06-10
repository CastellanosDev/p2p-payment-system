package com.fintech.p2p.application.service;

import com.fintech.p2p.domain.exception.UserNotFoundException;
import com.fintech.p2p.domain.model.User;
import com.fintech.p2p.domain.port.in.GetBalanceUseCase;
import com.fintech.p2p.domain.port.out.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Application service implementing the get-balance use case.
 */
@Service
public class GetBalanceService implements GetBalanceUseCase {

    private final UserRepository userRepository;

    public GetBalanceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(value = "balances", key = "#userId")
    public BigDecimal getBalance(Long userId) {
        // Look up the user; if not found, fail with a domain exception.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        // Return the balance held in the user's account.
        return user.getAccount().getBalance();
    }
}