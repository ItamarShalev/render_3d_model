package com.playking.adapter;

/**
 * Interface to force adapter to create the origin object.
 * @param <T> the object it's represent.
 */
public interface Adapter<T> {

    /**
     * Build the data to origin object instead of adapter.
     * @return the origin object
     */
    T build();
}
