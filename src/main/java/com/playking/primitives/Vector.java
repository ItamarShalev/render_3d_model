package com.playking.primitives;


import static com.playking.primitives.Util.alignZero;

import java.util.Arrays;
import java.util.List;

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
    public Vector(double x, double y, double z) throws IllegalArgumentException {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Can't create vector zero !");
        }
    }

    /**
     * Constructor create vector.
     * @param xyz object contains the three axes
     * @throws IllegalArgumentException if it's the zero vector
     */
    public Vector(Double3 xyz) throws IllegalArgumentException {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Can't create vector zero !");
        }
    }

    /**
     * Calculate the matrix rotation.
     * @param axis the given axis to calculate the matrix
     * @param theta the angle to rotate
     * @return the matrix rotation
     */
    public static List<Vector> getMatrixRotation(Vector axis, double theta) {
        double radian = -theta * Math.PI / 180;
        double x = axis.getX();
        double y = axis.getY();
        double z = axis.getZ();

        double cos = alignZero(Math.cos(radian));
        double sin = alignZero(Math.sin(radian));
        double cosMinus = (1 - cos);

        Vector rotateX = new Vector(x * x * cosMinus + cos, x * y * cosMinus - z * sin,
                                    x * z * cosMinus + y * sin);
        Vector rotateY = new Vector(y * x * cosMinus + z * sin, y * y * cosMinus + cos,
                                    y * z * cosMinus - x * sin);
        Vector rotateZ = new Vector(z * x * cosMinus - y * sin, z * y * cosMinus + x * sin,
                                    z * z * cosMinus + cos);
        return Arrays.asList(rotateX, rotateY, rotateZ);
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
    public Vector crossProduct(Vector vector) throws IllegalArgumentException {
        double x = getY() * vector.getZ() - getZ() * vector.getY();
        double y = getZ() * vector.getX() - getX() * vector.getZ();
        double z = getX() * vector.getY() - getY() * vector.getX();
        return new Vector(x, y, z);
    }

    /**
     * Multiply the current vector by the matrix.
     * @param matrix the matrix to multiply the current vector
     * @return the result vector after multiply by the matrix
     * @throws IllegalArgumentException if the matrix size isn't 3x3
     */
    public Vector matrixProduct(List<Vector> matrix) throws IllegalArgumentException {
        if (matrix.size() != 3) {
            throw new IllegalArgumentException("ERROR: Matrix must be 3x3");
        }
        double[] xyz = new double[3];
        for (int i = 0; i < 3; i++) {
            xyz[i] = dotProduct(matrix.get(i));
        }
        return new Vector(xyz[0], xyz[1], xyz[2]);
    }

    /**
     * Calculate multiply number in the current vector.
     * (current vector * number)
     * @param number the number to multiply every component of the current vector
     * @return the result vector after multiply by the number (current vector * number)
     * @throws IllegalArgumentException if the result vector is the zero vector
     */
    public Vector scale(double number) throws IllegalArgumentException {
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
     * Check if the vector is the same normal.
     * @param vector the vector to check
     * @return true if the current length and the vector param are parallel and length equals 1
     */
    public boolean isSameNormal(Vector vector) {
        if (!(Util.isZero(length() - 1) && Util.isZero(vector.length() - 1))) {
            return false;
        }
        return equals(vector) || equals(vector.scale(-1));
    }

    /**
     * Calculate addition between two vectors.
     * (current vector + param vector)
     * @param vector the vector to add to current vector
     * @return the result vector after addition between the vectors (current vector + param vector)
     * @throws IllegalArgumentException if the result vector is the zero vector
     */
    public Vector add(Vector vector) throws IllegalArgumentException {
        return new Vector(xyz.add(vector.xyz));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector && super.equals(obj);
    }

    @Override
    public String toString() {
        return "->" + super.toString();
    }
}
