package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Vector;

/**
 * Class describe plane,
 * contains some point on the surface and normal vector from the point.
 */
public class Plane implements Geometry {

    private final Point p0;
    private final Vector normal;

    /**
     * Constructor create plane from one point and normal vector from the point.
     * @param p0 some point on the surface
     * @param normal normal vector from the point
     */
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal.normalize();
    }

    /**
     * Constructor create plane from 3 points on the surface.
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     * @throws IllegalArgumentException if the points can't create plane (parallel etc.)
     */
    public Plane(Point p1, Point p2, Point p3) throws IllegalArgumentException {
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
            throw new IllegalArgumentException("Points can't be the same on the plane.");
        }
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        /* If the 3 points are on the same line the vectors will be parallel it will create exception */
        normal = v1.crossProduct(v2).normalize();
        p0 = p1;
    }


    public Point getP0() {
        return p0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}
