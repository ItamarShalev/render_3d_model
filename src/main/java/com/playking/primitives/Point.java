package com.playking.primitives;

/**
 * Class describe point3D.
 * all the method return new object and doesn't change the current one.
 */
public class Point {

    protected final Double3 xyz;

    /**
     * Constructor to create point3D.
     * @param x for axis x
     * @param y for axis y
     * @param z for axis z
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructor to create point3D.
     * @param xyz object contains the three axes
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Subtract point from the current point.
     * @param point the start point from to current point
     * @return vector with the direction from point to the current point
     * @throws IllegalArgumentException if the result vector is the zero vector
     */
    public Vector subtract(Point point) throws IllegalArgumentException {
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * Add point from to the current point.
     * @param vector the vector to add to the current point
     * @return point that start with the current point and the vector
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Calculate the square distance between two points.
     * @param point the point to calculate the square distance from the current point to it
     * @return the square distance between the two points
     */
    public double distanceSquared(Point point) {
        Double3 result = xyz.subtract(point.xyz);
        result = result.product(result);
        return result.sum();
    }

    /**
     * Calculate the distance between two points.
     * @param point the point to calculate the distance from the current point to it
     * @return the distance between the two points
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    public Double3 getXYZ() {
        return xyz;
    }

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }

    public double getZ() {
        return xyz.d3;
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point other = (Point)obj;
        return xyz.equals(other.xyz);
    }

    /**
     * Create string with the pattern,
     * (x, y, z).
     * @return string contains : "(x, y, z)"
     */
    @Override
    public String toString() {
        return "(" + xyz.d1 + ", " + xyz.d2 + ", " + xyz.d3 + ")";
    }
}
