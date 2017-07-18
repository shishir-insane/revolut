package com.sk.revolut.service;

import com.sk.revolut.exception.BalanceException;

/**
 * The Interface AccountService.
 */
public interface AccountService {
    
    /**
     * Transfer.
     *
     * @param fromAccount
     *            the from account
     * @param toAccount
     *            the to account
     * @param quantity
     *            the quantity
     * @throws BalanceException
     *             the balance exception
     */
    void transfer(String fromAccount, String toAccount, double quantity) throws BalanceException;

    /**
     * Gets the balance.
     *
     * @param accountId
     *            the account id
     * @return the balance
     */
    double getBalance(String accountId);
}
