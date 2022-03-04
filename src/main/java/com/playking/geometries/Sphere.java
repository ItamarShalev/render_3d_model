package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Vector;

/**
 * Class describe sphere.
 */
public class Sphere implements Geometry {

    private final Point center;
    private final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}
