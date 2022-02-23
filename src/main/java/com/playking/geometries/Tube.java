package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;

/**
 * Class describe tube, the tube is infinity.
 */
public class Tube implements Geometry {

    protected final Ray axisRay;
    protected final double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
