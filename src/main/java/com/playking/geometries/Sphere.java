package com.playking.geometries;

import static com.playking.primitives.Util.alignZero;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.List;

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        // in case that P0 is same as center
        Point p0 = ray.getP0();
        Vector vector = ray.getDir();

        if (p0.equals(center)) {
            return List.of(center.add(vector.scale(radius)));
        }

        Vector u = center.subtract(ray.getP0());
        double tm = alignZero(vector.dotProduct(u));
        double d = alignZero(Math.sqrt(alignZero(u.lengthSquared() - tm * tm)));
        double th = alignZero(Math.sqrt(alignZero(radius * radius - d * d)));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        // no intersection : the ray diraction is above the sphere
        if (d >= radius) {
            return null;
        }

        if (t1 > 0 && t2 > 0) {
            Point p1 = p0.add(vector.scale(t1));
            Point p2 = p0.add(vector.scale(t2));
            return List.of(p1, p2);
        }
        if (t1 > 0) {
            Point p1 = p0.add(vector.scale(t1));
            return List.of(p1);
        }
        if (t2 > 0) {
            Point p2 = p0.add(vector.scale(t2));
            return List.of(p2);
        }

        return null;
    }
}
