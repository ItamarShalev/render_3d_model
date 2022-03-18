package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.List;


/**
 * Class describe triangle, polygon with 3 points.
 */
public class Triangle extends Polygon {

    /**
     * Constructor create triangle from 3 points.
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     * @throws IllegalArgumentException if the points can't create triangle (same points etc.)
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // if ray dosnt intersect the plan consist in triangle return null
        if (this.plane.findIntersections(ray) == null) {
            return null;
        }
        Vector v = ray.getDir();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        // otherwise check if ray intersect the triangle
        Vector v1 = p1.subtract(ray.getP0());
        Vector v2 = p2.subtract(ray.getP0());
        Vector v3 = p3.subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        if ((v.dotProduct(n1) > 0 && v.dotProduct(n2) > 0 && v.dotProduct(n3) > 0) || (
            v.dotProduct(n1) < 0 && v.dotProduct(n2) < 0 && v.dotProduct(n3) < 0)) {
            List<Point> ret = this.plane.findIntersections(ray);
            return ret;
        } else {
            return null;
        }
    }
}
