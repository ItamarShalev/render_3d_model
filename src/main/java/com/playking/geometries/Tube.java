package com.playking.geometries;

import static com.playking.primitives.Util.alignZero;
import static com.playking.primitives.Util.isZero;

import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import java.util.List;

/**
 * Class describe tube, the tube is infinity.
 */
public class Tube extends Geometry {

    protected final Ray axisRay;
    protected final double radius;

    /**
     * Create tube.
     * @param axisRay the ray contains the direction and the center point of the center
     * @param radius the radius of the tube
     * @throws IllegalArgumentException if the radius is negative or zero
     */
    public Tube(Ray axisRay, double radius) {
        if (radius < 0 || isZero(radius)) {
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
        if (isZero(t)) {
            return pMinusHead.normalize();
        }
        /* The point on the side calculate the normal */
        Point center = axisRay.getP0().add(axisRay.getDir().scale(t));
        return point.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector v = ray.getDir();
        Vector v0 = axisRay.getDir();

        /* Calculating temp1 = v - v0 * (v,v0) */
        Vector temp1 = v;
        double vv0 = v.dotProduct(v0);
        if (!isZero(vv0)) {
            Vector v0vv0 = v0.scale(vv0);
            if (v.equals(v0vv0)) {
                return null;
            }
            temp1 = v.subtract(v0vv0);
        }

        /* Calculating temp2 = dp - v0 * (dp,v0) where dp = p0 - p */
        double temp1DotTemp2 = 0;
        double squaredTemp2 = 0;
        if (!ray.getP0().equals(axisRay.getP0())) {
            Vector dp = ray.getP0().subtract(axisRay.getP0());
            Vector temp2 = dp;
            double dpv0 = dp.dotProduct(v0);
            if (isZero(dpv0)) {
                temp1DotTemp2 = temp1.dotProduct(temp2);
                squaredTemp2 = temp2.lengthSquared();
            } else {
                Vector v0dpv0 = v0.scale(dpv0);
                if (!dp.equals(v0dpv0)) {
                    temp2 = dp.subtract(v0dpv0);
                    temp1DotTemp2 = temp1.dotProduct(temp2);
                    squaredTemp2 = temp2.lengthSquared();
                }
            }
        }

        /* Getting the quadratic equation: at^2 +bt + c = 0 */
        double a = temp1.lengthSquared();
        double b = 2 * temp1DotTemp2;
        double c = alignZero(squaredTemp2 - radius * radius);

        double squaredDelta = alignZero(b * b - 4 * a * c);
        if (squaredDelta <= 0) {
            return null;
        }

        double delta = Math.sqrt(squaredDelta);
        double t1 = alignZero((-b + delta) / (2 * a));
        double t2 = alignZero((-b - delta) / (2 * a));

        if (t1 > 0 && t2 > 0) {
            return List.of(new GeoPoint(this, ray.getP0(t1)), new GeoPoint(this, ray.getP0(t2)));
        }
        if (t1 > 0) {
            return List.of(new GeoPoint(this, ray.getP0(t1)));
        }
        if (t2 > 0) {
            return List.of(new GeoPoint(this, ray.getP0(t2)));
        }

        return null;
    }
}
