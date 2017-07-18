package com.sk.revolut.exception;

/**
 * The Class BalanceException.
 */
public class BalanceException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 3655089629938517021L;

    /**
     * Instantiates a new balance exception.
     *
     * @param message
     *            the message
     */
    public BalanceException(final String message) {
        super(message);
    }
}
