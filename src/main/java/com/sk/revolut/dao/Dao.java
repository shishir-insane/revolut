package com.sk.revolut.dao;

import java.util.Set;

/**
 * The Interface Dao.
 *
 * @param <K>
 *            the key type
 * @param <V>
 *            the value type
 */
public interface Dao<K, V> {
    
    /**
     * Gets the.
     *
     * @param key
     *            the key
     * @return the v
     */
    V get(K key);

    /**
     * Gets the all.
     *
     * @return the all
     */
    Set<V> getAll();

    /**
     * Adds the or update.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @throws IllegalArgumentException
     *             the illegal argument exception
     */
    void addOrUpdate(K key, V value) throws IllegalArgumentException;

    /**
     * Delete.
     *
     * @param key
     *            the key
     */
    void delete(K key);

}
