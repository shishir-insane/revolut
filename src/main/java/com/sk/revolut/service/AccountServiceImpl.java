package com.sk.revolut.service;

import static com.sk.revolut.utils.Utils.error;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import com.sk.revolut.dao.Dao;
import com.sk.revolut.domain.UserAccount;
import com.sk.revolut.exception.BalanceException;

/**
 * The Class AccountServiceImpl.
 */
public class AccountServiceImpl implements AccountService {
    private static final Logger LOG = Logger.getLogger(AccountServiceImpl.class);

    private final ReentrantReadWriteLock accountLock = new ReentrantReadWriteLock();

    private final Dao<String, UserAccount> accountDao;

    /**
     * Instantiates a new account service impl.
     *
     * @param accountDao
     *            the account dao
     */
    public AccountServiceImpl(final Dao<String, UserAccount> accountDao) {
        this.accountDao = accountDao;
    }

    /* (non-Javadoc)
     * @see com.sk.revolut.service.AccountService#transfer(java.lang.String, java.lang.String, double)
     */
    @Override
    public void transfer(final String fromAccount, final String toAccount, final double quantity)
            throws BalanceException {
        if (fromAccount == null) {
            error("null from account id passed to transfer", IllegalArgumentException.class);
        }

        if (toAccount == null) {
            error("null to account id passed to transfer", IllegalArgumentException.class);
        }

        if (fromAccount.equals(toAccount)) {
            error("Cannot transfer to and from same account", IllegalArgumentException.class);
        }

        if (quantity <= 0) {
            error("quantity must be greater than zero for transfer", IllegalArgumentException.class);
        }

        LOG.info("Attempting to transfer " + quantity + " from " + fromAccount + " to " + toAccount);
        try {
            accountLock.writeLock().lock();
            final UserAccount from = accountDao.get(fromAccount);
            final UserAccount to = accountDao.get(toAccount);
            if (from == null) {
                throw new IllegalStateException("No account found with id " + fromAccount);
            }
            if (to == null) {
                throw new IllegalStateException("No account found with id " + toAccount);
            }

            if (from.getBalance() - quantity < 0) {
                throw new BalanceException(
                        "Not enough funds in account " + fromAccount + " when transferring " + quantity);
            }
            to.add(quantity);
            from.add(-quantity);
            accountDao.addOrUpdate(fromAccount, from);
            accountDao.addOrUpdate(toAccount, to);
            LOG.info("Successfully transferred " + quantity + " from " + fromAccount + " to " + toAccount);
        } finally {
            accountLock.writeLock().unlock();
        }
    }

    /* (non-Javadoc)
     * @see com.sk.revolut.service.AccountService#getBalance(java.lang.String)
     */
    @Override
    public double getBalance(final String accountId) {
        if (accountId == null) {
            error("null acount id passed to getBalance", IllegalArgumentException.class);
        }
        LOG.info("Attempting to get balance for account with id " + accountId);
        try {
            accountLock.readLock().lock();
            final UserAccount account = accountDao.get(accountId);
            if (account == null) {
                error("No account found for id " + accountId, IllegalStateException.class);
            }

            return account.getBalance();
        } finally {
            accountLock.readLock().unlock();
        }

    }
}
