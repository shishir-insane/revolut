package com.sk.revolut.dao;

import static com.sk.revolut.utils.Utils.error;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.sk.revolut.domain.UserAccount;

/**
 * The Class AccountDaoInMemoryImpl.
 */
public class AccountDaoInMemoryImpl implements Dao<String, UserAccount> {
    private static final Logger LOG = Logger.getLogger(AccountDaoInMemoryImpl.class);

    private final Map<String, UserAccount> accountMap = Maps.newConcurrentMap();

    /* (non-Javadoc)
     * @see com.sk.revolut.dao.Dao#get(java.lang.Object)
     */
    @Override
    public UserAccount get(final String key) {
        if (key == null) {
            LOG.warn("Null key passed to get");
            return null;
        }
        LOG.info("Getting account for id " + key);

        return accountMap.get(key);
    }

    /* (non-Javadoc)
     * @see com.sk.revolut.dao.Dao#getAll()
     */
    @Override
    public Set<UserAccount> getAll() {
        LOG.info("Getting all accounts");
        return Collections.unmodifiableSet(Sets.newHashSet(accountMap.values()));
    }

    /* (non-Javadoc)
     * @see com.sk.revolut.dao.Dao#addOrUpdate(java.lang.Object, java.lang.Object)
     */
    @Override
    public void addOrUpdate(final String key, final UserAccount value) throws IllegalArgumentException {
        if (key == null) {
            error("Cannot add account for null id", IllegalArgumentException.class);
        }
        if (value == null) {
            error("Cannot add a null account entry", IllegalArgumentException.class);
        }
        accountMap.put(key, value);
    }

    /* (non-Javadoc)
     * @see com.sk.revolut.dao.Dao#delete(java.lang.Object)
     */
    @Override
    public void delete(final String key) {
        if (key == null) {
            LOG.warn("Cannot delete null key");
            return;
        }
        accountMap.remove(key);
    }
}
