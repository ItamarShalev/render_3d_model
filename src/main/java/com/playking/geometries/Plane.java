package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Vector;

public class Plane implements Geometry {

    private Point point;

    public Plane(Point pointX, Point pointY, Point pointZ) {
    }

    public Vector getNormal() {
        return getNormal(point);
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
