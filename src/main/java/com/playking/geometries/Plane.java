package com.playking.geometries;

import static com.playking.primitives.Util.alignZero;
import static com.playking.primitives.Util.isZero;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.List;

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
     * @throws IllegalArgumentException if the points on the same line some points are equals
     */
    public Plane(Point p1, Point p2, Point p3) throws IllegalArgumentException {
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
            throw new IllegalArgumentException("Points can't be the same on the plane.");
        }
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        /* Checks if the dots on it are straight, then throws an exception. */
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

    /**
     * Finds the intersections of a Ray with the current object.
     * @param ray The ray to intersect
     * @return List of the intersections - 3D points
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Point p = null;
        if (this.p0.equals(p0)) {
            return null;
        }

        Vector v = ray.getDir();
        Vector n = normal;
        Vector p0DistanceQ0 = this.p0.subtract(p0);
        double numerator = alignZero(n.dotProduct(p0DistanceQ0));
        double denominator = alignZero(n.dotProduct(v));
        double t = alignZero(numerator / denominator);

        if (isZero(numerator)) {
            return null;
        }
        // ray is lying in the plane axis
        if (isZero(denominator)) {
            return null;
        }
        if (t <= 0) {
            return null;
        }

        p = ray.getP0(t);
        return List.of(p);
    }
}
