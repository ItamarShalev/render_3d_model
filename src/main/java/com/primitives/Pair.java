package com.primitives;

/**
 * Class to hold two objects.
 * @param <First> the type of the first object
 * @param <Second> the type of the second object
 */
public class Pair<First, Second> {

    public First first;
    public Second second;

    public Pair() {
    }

    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }
}
