package com.playking.geometries;

import static com.playking.primitives.Util.isZero;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.LinkedList;
import java.util.List;

/**
 * Class describe cylinder not infinite tube.
 */
public class Cylinder extends Tube {

    protected final Plane bottomCap, topCap;
    private final double height;

    /**
     * Create non infinity tube.
     * @param axisRay the ray contains the direction and the center point of the center
     * @param radius the radius of the tube
     * @param height the height of the cylinder
     * @throws IllegalArgumentException if height or radius are negative or zero
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if (height < 0 || isZero(height)) {
            throw new IllegalArgumentException("Height can't be zero or negative.");
        }
        this.height = height;
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getP0(height);
        /* Sets the normal directed outside the cylinder */
        bottomCap = new Plane(p0, axisRay.getDir().scale(-1));
        topCap = new Plane(p1, axisRay.getDir());
    }

    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        Point p0 = axisRay.getP0();
        /* The point is on the bottom */
        Vector vector, vector1;
        Point point1;
        /* The point is exactly in the center. */
        if (point.equals(p0)) {
            return axisRay.getDir().scale(-1);
        }
        vector = p0.subtract(point);
        /* The point on the bottom but not in the center */
        if (isZero(vector.dotProduct(axisRay.getDir()))) {
            return axisRay.getDir().scale(-1);
        }

        point1 = p0.add(axisRay.getDir().scale(height));

        /* The point is exactly in the center. */
        if (point.equals(point1)) {
            return axisRay.getDir();
        }
        vector1 = point1.subtract(point);
        /* The point on the top but not in the center */
        if (isZero(vector1.dotProduct(axisRay.getDir()))) {
            return axisRay.getDir();
        }
        /* The point on the side, handle it like a tube. */
        return super.getNormal(point);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point point1 = axisRay.getP0();
        Point point2 = axisRay.getP0(height);
        List<GeoPoint> result = null;
        /* Find the tube's intersections */
        List<GeoPoint> tubePoints = super.findGeoIntersectionsHelper(ray, maxDistance);
        GeoPoint geoPoint1;
        GeoPoint geoPoint2;
        boolean q0Intersects;
        boolean q1Intersects;

        if (tubePoints != null) {
            if (tubePoints.size() == 2) {
                /* Checks if the intersection points are on the cylinder */
                geoPoint1 = tubePoints.get(0);
                geoPoint2 = tubePoints.get(1);
                q0Intersects = isBetweenCaps(geoPoint1.point);
                q1Intersects = isBetweenCaps(geoPoint2.point);

                if (q0Intersects && q1Intersects) {
                    return tubePoints;
                }

                if (q0Intersects) {
                    result = new LinkedList<>();
                    result.add(geoPoint1);
                } else if (q1Intersects) {
                    result = new LinkedList<>();
                    result.add(geoPoint2);
                }
            }

            if (tubePoints.size() == 1) {
                /* Checks if the intersection point is on the cylinder */
                GeoPoint q = tubePoints.get(0);
                if (isBetweenCaps(q.point)) {
                    result = new LinkedList<>();
                    result.add(q);
                }
            }
        }
        /* Finds the bottom cap's intersections */
        List<GeoPoint> cap0Point = bottomCap.findGeoIntersectionsHelper(ray, maxDistance);
        if (cap0Point != null) {
            // Checks if the intersection point is on the cap */
            geoPoint1 = cap0Point.get(0);
            if (geoPoint1.point.distanceSquared(point1) < radius * radius) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.add(geoPoint1);
                if (result.size() == 2) {
                    return result;
                }
            }
        }
        /* Finds the top cap's intersections */
        List<GeoPoint> cap1Point = topCap.findGeoIntersectionsHelper(ray, maxDistance);
        if (cap1Point != null) {
            /* Checks if the intersection point is on the cap */
            geoPoint1 = cap1Point.get(0);
            if (geoPoint1.point.distanceSquared(point2) < radius * radius) {
                if (result == null) {
                    return List.of(geoPoint1);
                }

                result.add(geoPoint1);
            }
        }

        return result;
    }

    /**
     * Helper function that checks if a points is between the two caps.
     * @param p The point that will be checked.
     * @return True if it is between the caps. Otherwise, false.
     */
    private boolean isBetweenCaps(Point p) {
        Vector vector = axisRay.getDir();
        Point point1 = axisRay.getP0();
        Point point2 = axisRay.getP0(height);
        /* Checks against zero vector... */
        if (p.equals(point1) || p.equals(point2)) {
            return false;
        }
        return vector.dotProduct(p.subtract(point1)) > 0
               && vector.dotProduct(p.subtract(point2)) < 0;
    }
}

