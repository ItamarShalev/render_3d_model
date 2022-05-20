package com.geometries;

import static com.primitives.Util.isZero;

import com.primitives.Point;
import com.primitives.Ray;
import com.primitives.Vector;
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        /* t=n*(q0-Po)/n*dir */
        Vector dir = ray.getDir();
        Point p0 = ray.getP0();

        /* Ray on the plane */
        if (this.p0.equals(p0)) {
            return null;
        }

        double nqp = normal.dotProduct(this.p0.subtract(p0));
        /* Ray on the plane */
        if (isZero(nqp)) {
            return null;
        }

        double nv = normal.dotProduct(dir);
        if (isZero(nv)) {
            return null;
        }

        double t = nqp / nv;
        /* Ray after the plane */
        if (t < 0) {
            return null;
        }

        p0 = ray.getP0(t);
        /* Ray crosses the plane */
        if (p0 != null) {
            if (p0.distance(ray.getP0()) <= maxDistance) {
                return List.of(new GeoPoint(this, p0));
            }
        }
        return null;
    }
}
