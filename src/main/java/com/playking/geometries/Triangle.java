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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        /* If ray doesn't intersect the plan consist in triangle return null */
        List<GeoPoint> planeGeoIntersectionsHelper = plane.findGeoIntersectionsHelper(ray);
        if (planeGeoIntersectionsHelper == null) {
            return null;
        }
        List<GeoPoint> result = null;
        Vector vector, vector1, vector2, vector3;
        Vector normal1, normal2, normal3;
        Point point1, point2, point3;
        boolean isThereIntersections;

        vector = ray.getDir();

        point1 = vertices.get(0);
        point2 = vertices.get(1);
        point3 = vertices.get(2);

        /* Otherwise, check if ray intersect the triangle */
        vector1 = point1.subtract(ray.getP0());
        vector2 = point2.subtract(ray.getP0());
        vector3 = point3.subtract(ray.getP0());

        normal1 = vector1.crossProduct(vector2);
        normal2 = vector2.crossProduct(vector3);
        normal3 = vector3.crossProduct(vector1);

        isThereIntersections = vector.dotProduct(normal1) > 0 && vector.dotProduct(normal2) > 0 &&
                               vector.dotProduct(normal3) > 0;

        isThereIntersections = isThereIntersections ||
                               vector.dotProduct(normal1) < 0 && vector.dotProduct(normal2) < 0 &&
                               vector.dotProduct(normal3) < 0;

        if (isThereIntersections) {
            result = List.of(new GeoPoint(this, planeGeoIntersectionsHelper.get(0).point));
        }
        return result;
    }
}
