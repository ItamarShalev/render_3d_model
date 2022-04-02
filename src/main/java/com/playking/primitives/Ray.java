package com.playking.primitives;


import com.playking.geometries.Intersect.GeoPoint;
import java.util.List;

/**
 * Class describe ray, start with some point and has a direction.
 */
public class Ray {

    private final Point p0;
    private final Vector dir;

    /**
     * Constructor.
     * @param p0 start point
     * @param dir direction vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Point getP0(double t) {
        return p0.add(dir.scale(t));
    }

    public Vector getDir() {
        return dir;
    }

    /**
     * Find the closest point to current ray.
     * @param points list of points
     * @return the closest point to ray, if there are no points, return null
     */
    public Point findClosestPoint(List<Point> points) {
        Point closestPoint = null;
        if (points != null && !points.isEmpty()) {
            closestPoint = findClosestGeoPoint(
                points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
        }
        return closestPoint;
    }

    /**
     * Find the closest point to current ray.
     * @param points list of geo points
     * @return the closest geo point to ray, if there are no points, return null
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        GeoPoint closestGeoPoint = null;
        double minDistance = 0;
        double distance = 0;
        if (points == null || points.isEmpty()) {
            return null;
        }

        for (GeoPoint geoPoint : points) {
            if (closestGeoPoint == null) {
                closestGeoPoint = geoPoint;
                minDistance = closestGeoPoint.point.distanceSquared(p0);
                continue;
            }
            distance = geoPoint.point.distanceSquared(p0);
            if (distance < minDistance) {
                closestGeoPoint = geoPoint;
                minDistance = distance;
            }
        }
        return closestGeoPoint;
    }

    @Override
    public int hashCode() {
        return p0.hashCode() + dir.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Ray)) {
            return false;
        }
        Ray other = (Ray)obj;
        return p0.equals(other.p0) && dir.equals(other.dir);
    }

    /**
     * Create string with the pattern,
     * (x, y, z) -> (dirX, dirY, dirZ).
     * @return string contains : "(x, y, z) -> (dirX, dirY, dirZ)"
     */
    @Override
    public String toString() {
        return p0 + " -> " + dir;
    }
}
