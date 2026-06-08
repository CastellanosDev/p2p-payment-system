package com.fintech.p2p.domain.port.in;

import com.fintech.p2p.domain.exception.UserNotFoundException;

import java.math.BigDecimal;

public interface GetBalanceUseCase {


    /**
     * Returns the current balance of the user with the given id.
     * @throws UserNotFoundException if no user exists with that id.
     */
    BigDecimal getBalance(Long userId);
}
