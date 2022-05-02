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
public class Plane extends Geometry {

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
        /* If there are some points that equals IllegalArgumentException will be thrown here */
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        /* Checks if the dots on it are straight, then IllegalArgumentException will be thrown */
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
     * @return List of points all the intersections, if there is no intersections return null
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (p0.equals(ray.getP0())) {
            return null;
        }
        List<GeoPoint> result = null;
        Vector p0DistanceQ0;
        Vector vector = ray.getDir();
        double numerator, denominator, t;
        boolean isThereNoIntersections;

        p0DistanceQ0 = p0.subtract(ray.getP0());
        numerator = alignZero(normal.dotProduct(p0DistanceQ0));
        denominator = alignZero(normal.dotProduct(vector));
        t = alignZero(numerator / denominator);
        isThereNoIntersections = isZero(numerator) || isZero(denominator) || t <= 0;
        if (!isThereNoIntersections) {
            result = List.of(new GeoPoint(this, ray.getP0(t)));
        }

        return result;
    }
}
