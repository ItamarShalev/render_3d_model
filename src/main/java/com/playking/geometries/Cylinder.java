package com.playking.geometries;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Util;
import com.playking.primitives.Vector;
import java.util.List;

/**
 * Class describe cylinder not infinite tube.
 */
public class Cylinder extends Tube {

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
        if (height < 0 || Util.isZero(height)) {
            throw new IllegalArgumentException("Height can't be zero or negative.");
        }
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        Point p0 = axisRay.getP0();
        /* The point is on the bottom */
        Vector v0;
        try {
            v0 = p0.subtract(point);
        } catch (IllegalArgumentException ignored) {
            /* The point is exactly in the center. */
            return axisRay.getDir().scale(-1);
        }
        if (Util.isZero(v0.dotProduct(axisRay.getDir()))) {
            /* The point on the bottom but not in the center */
            return axisRay.getDir().scale(-1);
        }

        Point p1 = p0.add(axisRay.getDir().scale(height));
        Vector v1;
        try {
            v1 = p1.subtract(point);
        } catch (IllegalArgumentException ignored) {
            /* The point is exactly in the center. */
            return axisRay.getDir();
        }
        if (Util.isZero(v1.dotProduct(axisRay.getDir()))) {
            /* The point on the top but not in the center */
            return axisRay.getDir();
        }
        /* The point on the side, handle it like a tube. */
        return super.getNormal(point);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}
