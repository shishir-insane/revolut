package com.sk.revolut.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

/**
 * The Class Utils.
 */
public class Utils {
    private static final Logger LOG = Logger.getLogger(Utils.class);

    /**
     * Error.
     *
     * @param <T>
     *            the generic type
     * @param message
     *            the message
     * @param errClass
     *            the err class
     * @throws T
     *             the t
     */
    public static <T extends Throwable> void error(final String message, final Class<T> errClass) throws T {
        LOG.error(message);
        try {
            throw errClass.getConstructor(String.class).newInstance(message);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
