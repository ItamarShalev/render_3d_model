package com.playking.primitives;


/**
 * Class describe vector.
 * all the method return new vector and doesn't change the current one.
 */
public class Vector extends Point {


    /**
     * Constructor create vector.
     * @param x axis x
     * @param y axis y
     * @param z axis z
     * @throws IllegalArgumentException if it's the zero vector
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Constructor create vector.
     * @param xyz object contains the three axes
     * @throws IllegalArgumentException if it's the zero vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Can't create vector zero !");
        }
    }


    /**
     * Calculate dot product between two vectors.
     * (current vector * param vector)
     * @param vector the vector to product with current vector
     * @return Amount of double components
     */
    public double dotProduct(Vector vector) {
        return xyz.product(vector.xyz).sum();
    }

    /**
     * Calculate cross product between two vectors.
     * (current vector X param vector)
     * @param vector the vector to product with current vector
     * @return the result vector after cross product between the vectors
     *     (current vector X param vector)
     * @throws IllegalArgumentException if the vectors are parallel
     */
    public Vector crossProduct(Vector vector) {
        double x = xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2;
        double y = xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3;
        double z = xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1;
        return new Vector(x, y, z);
    }

    /**
     * Calculate multiply number in the current vector.
     * (current vector * number)
     * @param number the number to multiply every component of the current vector
     * @return the result vector after multiply by the number (current vector * number)
     * @throws IllegalArgumentException if the result vector is the zero vector
     */
    public Vector scale(float number) {
        return new Vector(xyz.scale(number));
    }

    /**
     * Calculate the square length of the vector.
     * @return square length of the current vector
     */
    public double lengthSquared() {
        return xyz.product(xyz).sum();
    }

    /**
     * Calculate the length of the vector.
     * @return length of the current vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Calculate unit vector from the current vector.
     * @return unit vector from the current one
     */
    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }

    /**
     * Calculate addition between two vectors.
     * (current vector + param vector)
     * @param vector the vector to add to current vector
     * @return the result vector after addition between the vectors (current vector + param vector)
     * @throws IllegalArgumentException if the result vector is the zero vector
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vector)) {
            return false;
        }
        Vector other = (Vector)obj;
        return xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "->" + super.toString();
    }
}
