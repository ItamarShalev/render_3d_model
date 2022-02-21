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
        this.normal = normal;
    }

    /**
     * Constructor create plane from 3 points on the surface.
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     * @throws IllegalArgumentException if the points can't create plane (parallel etc.)
     */
    public Plane(Point p1, Point p2, Point p3) {
        p0 = p1;
        normal = null;
    }


    public Point getP0() {
        return p0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
