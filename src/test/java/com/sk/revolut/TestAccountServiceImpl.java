package com.sk.revolut;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sk.revolut.dao.Dao;
import com.sk.revolut.domain.UserAccount;
import com.sk.revolut.exception.BalanceException;
import com.sk.revolut.service.AccountService;
import com.sk.revolut.service.AccountServiceImpl;

/**
 * The Class TestAccountServiceImpl.
 */
public class TestAccountServiceImpl {
    private static final UserAccount TEST_ACCOUNT = new UserAccount("accId", 200);
    private static final UserAccount TEST_ACCOUNT2 = new UserAccount("accId2", 100);

    private Dao<String, UserAccount> accountDao;
    private AccountService accountService;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        accountDao = Mockito.mock(Dao.class);
        when(accountDao.get(TEST_ACCOUNT.getAccountId())).thenReturn(TEST_ACCOUNT);
        when(accountDao.get(TEST_ACCOUNT2.getAccountId())).thenReturn(TEST_ACCOUNT2);
        when(accountDao.get(null)).thenReturn(null);

        accountService = new AccountServiceImpl(accountDao);
    }

    /**
     * Test can transfer valid amount.
     *
     * @throws BalanceException
     *             the balance exception
     */
    @Test
    public void testCanTransferValidAmount() throws BalanceException {
        accountService.transfer(TEST_ACCOUNT.getAccountId(), TEST_ACCOUNT2.getAccountId(), 100);
        final double a1Balance = accountService.getBalance(TEST_ACCOUNT.getAccountId());
        final double a2Balance = accountService.getBalance(TEST_ACCOUNT2.getAccountId());
        Assert.assertTrue("From has decreased by 100", new Double(a1Balance).compareTo(100.0d) == 0);
        Assert.assertTrue("To has increased by 100", new Double(a2Balance).compareTo(200.0d) == 0);
    }

    /**
     * Test cannot transfer too large amount.
     *
     * @throws BalanceException
     *             the balance exception
     */
    @Test(expected = BalanceException.class)
    public void testCannotTransferTooLargeAmount() throws BalanceException {
        accountService.transfer(TEST_ACCOUNT.getAccountId(), TEST_ACCOUNT2.getAccountId(), 300);
    }

    /**
     * Test cannot transfer to same account.
     *
     * @throws BalanceException
     *             the balance exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCannotTransferToSameAccount() throws BalanceException {
        accountService.transfer(TEST_ACCOUNT.getAccountId(), TEST_ACCOUNT.getAccountId(), 100);
    }

    /**
     * Test cannot transfer null from account.
     *
     * @throws BalanceException
     *             the balance exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCannotTransferNullFromAccount() throws BalanceException {
        accountService.transfer(null, TEST_ACCOUNT.getAccountId(), 100);
    }

    /**
     * Test cannot transfer null to account.
     *
     * @throws BalanceException
     *             the balance exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCannotTransferNullToAccount() throws BalanceException {
        accountService.transfer(TEST_ACCOUNT.getAccountId(), null, 100);
    }

    /**
     * Test cannot transfer zero.
     *
     * @throws BalanceException
     *             the balance exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCannotTransferZero() throws BalanceException {
        accountService.transfer(TEST_ACCOUNT.getAccountId(), TEST_ACCOUNT2.getAccountId(), 0);
    }

    /**
     * Test cannot transfer less than zero.
     *
     * @throws BalanceException
     *             the balance exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCannotTransferLessThanZero() throws BalanceException {
        accountService.transfer(TEST_ACCOUNT.getAccountId(), TEST_ACCOUNT2.getAccountId(), -1);
    }
}
