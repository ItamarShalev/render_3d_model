package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Util;
import com.playking.primitives.Vector;

/**
 * Class describe tube, the tube is infinity.
 */
public class Tube implements Geometry {

    protected final Ray axisRay;
    protected final double radius;

    /**
     * Create tube.
     * @param axisRay the ray contains the direction and the center point of the center
     * @param radius the radius of the tube
     * @throws IllegalArgumentException if the radius is negative or zero
     */
    public Tube(Ray axisRay, double radius) {
        if (radius < 0 || Util.isZero(radius)) {
            throw new IllegalArgumentException("Radius can't be zero or negative.");
        }
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
        Vector pMinusHead = point.subtract(axisRay.getP0());
        double t = axisRay.getDir().dotProduct(pMinusHead);
        /* Check if the point is "front" to the p0 the point in the base */
        if (Util.isZero(t)) {
            return pMinusHead.normalize();
        }
        /* The point on the side calculate the normal */
        Point center = axisRay.getP0().add(axisRay.getDir().scale(t));
        return point.subtract(center).normalize();
    }
}
