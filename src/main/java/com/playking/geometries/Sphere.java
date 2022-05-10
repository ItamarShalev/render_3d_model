package com.playking.geometries;

import static com.playking.primitives.Util.alignZero;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.LinkedList;
import java.util.List;

/**
 * Class describe sphere.
 */
public class Sphere extends Geometry {

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

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        /* In case that point is same as center */
        if (ray.getP0().equals(center)) {
            return List.of(new GeoPoint(this, center.add(ray.getDir().scale(radius))));
        }
        List<GeoPoint> result = new LinkedList<>();
        double vectDPSubV, d, th, t1, t2;
        Point point = ray.getP0();
        Vector vector = ray.getDir();
        Vector subV;

        subV = center.subtract(point);
        vectDPSubV = alignZero(vector.dotProduct(subV));
        d = alignZero(Math.sqrt(alignZero(subV.lengthSquared() - vectDPSubV * vectDPSubV)));
        th = alignZero(Math.sqrt(alignZero(radius * radius - d * d)));
        t1 = alignZero(vectDPSubV - th);
        t2 = alignZero(vectDPSubV + th);
        if (d >= radius) {
            return null;
        }
        Point point1;
        Point point2;
        double distance1;
        double distance2;
        if (t1 > 0 && t2 > 0) {
            point1 = point.add(vector.scale(t1));
            point2 = point.add(vector.scale(t2));
            distance1 = point1.distance(point);
            distance2 = point2.distance(point);
            if (distance1 <= maxDistance && distance2 <= maxDistance) {
                return List.of(new GeoPoint(this, point1),
                               new GeoPoint(this, point2));
            } else if (distance1 <= maxDistance) {
                return List.of(new GeoPoint(this, point1));
            } else if (distance2 <= maxDistance) {
                return List.of(new GeoPoint(this, point2));
            } else {
                return null;
            }
        }
        if (t1 > 0) {
            point1 = point.add(vector.scale(t1));
            distance1 = point1.distance(ray.getP0());
            if (distance1 <= maxDistance) {
                return List.of(new GeoPoint(this, point1));
            }
        }
        if (t2 > 0) {
            point2 = point.add(vector.scale(t2));
            distance2 = point2.distance(ray.getP0());
            if (distance2 <= maxDistance) {
                return List.of(new GeoPoint(this, point2));
            }
        }
        return null;
    }
}
