package com.sk.revolut.domain;

/**
 * The Class UserAccount.
 */
public class UserAccount {

    private final String accountId;
    private double balance;

    /**
     * Instantiates a new user account.
     *
     * @param accountId
     *            the account id
     * @param balance
     *            the balance
     */
    public UserAccount(final String accountId, final double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    /**
     * Gets the account id.
     *
     * @return the account id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Gets the balance.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Adds the.
     *
     * @param amount
     *            the amount
     */
    public void add(final double amount) {
        balance += amount;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UserAccount account = (UserAccount) o;

        return accountId.equals(account.accountId);

    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return accountId.hashCode();
    }
}
