package com.playking.geometries;

import static com.playking.primitives.Util.isZero;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
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
        if (height < 0 || isZero(height)) {
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
        Vector vector, vector1;
        Point point1;
        /* The point is exactly in the center. */
        if (point.equals(p0)) {
            return axisRay.getDir().scale(-1);
        }
        vector = p0.subtract(point);
        /* The point on the bottom but not in the center */
        if (isZero(vector.dotProduct(axisRay.getDir()))) {
            return axisRay.getDir().scale(-1);
        }

        point1 = p0.add(axisRay.getDir().scale(height));

        /* The point is exactly in the center. */
        if (point.equals(point1)) {
            return axisRay.getDir();
        }
        vector1 = point1.subtract(point);
        /* The point on the top but not in the center */
        if (isZero(vector1.dotProduct(axisRay.getDir()))) {
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
