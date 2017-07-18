package com.sk.revolut;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sk.revolut.dao.AccountDaoInMemoryImpl;
import com.sk.revolut.dao.Dao;
import com.sk.revolut.domain.UserAccount;

/**
 * The Class TestAccountDaoInMemoryImpl.
 */
public class TestAccountDaoInMemoryImpl {

    private static final UserAccount TEST_ACCOUNT = new UserAccount("accId", 200);

    private Dao<String, UserAccount> accountDao;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        accountDao = new AccountDaoInMemoryImpl();
    }

    /**
     * Test account can be added and deleted.
     */
    @Test
    public void testAccountCanBeAddedAndDeleted() {
        accountDao.addOrUpdate(TEST_ACCOUNT.getAccountId(), TEST_ACCOUNT);
        Set<UserAccount> userAccounts = accountDao.getAll();
        Assert.assertTrue("Account is present in getAll",
                userAccounts.size() == 1 && userAccounts.contains(TEST_ACCOUNT));
        Assert.assertTrue("Account can be retrieved with key",
                accountDao.get(TEST_ACCOUNT.getAccountId()).equals(TEST_ACCOUNT));

        accountDao.delete(TEST_ACCOUNT.getAccountId());
        userAccounts = accountDao.getAll();
        Assert.assertTrue("Account is not present in getAll", userAccounts.isEmpty());
        Assert.assertTrue("Account cannot be retrieved with key", accountDao.get(TEST_ACCOUNT.getAccountId()) == null);
    }

    /**
     * Test exception thrown when account id is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExceptionThrownWhenAccountIdIsNull() {
        accountDao.addOrUpdate(null, TEST_ACCOUNT);
    }

    /**
     * Test exception thrown when account is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExceptionThrownWhenAccountIsNull() {
        accountDao.addOrUpdate("key", null);
    }
}
