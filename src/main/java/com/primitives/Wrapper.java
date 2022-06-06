package com.primitives;


/**
 * Class to hold variable for reference use.
 * @param <Variable> the type of the variable
 */
public class Wrapper<Variable> {
    public Variable variable;

    public Wrapper() {
    }

    public Wrapper(Variable variable) {
        this.variable = variable;
    }
}
