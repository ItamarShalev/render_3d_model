package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Vector;

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
}
