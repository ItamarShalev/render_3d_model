package com.playking.renderer;

import static com.playking.primitives.Util.alignZero;

import com.playking.geometries.Intersect.GeoPoint;
import com.playking.lighting.LightSource;
import com.playking.primitives.Color;
import com.playking.primitives.Double3;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import com.playking.scene.Scene;
import java.util.List;

/**
 * Implement RayTracerBase to handle all the ray trace.
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Constructor.
     * @param scene the scene
     * @throws NullPointerException if scene is null
     */
    public RayTracerBasic(Scene scene) throws NullPointerException {
        super(scene);
    }

    /**
     * Calculate the color of some point.
     * @param intersection the geoPoint to calculate the color
     * @param ray the ray to calculate the color
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(intersection.geometry.getEmission())
                                 .add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculate the local effects of the point.
     * @param intersection the geoPoint to calculate the local effects
     * @param ray the ray to calculate the local effects
     * @return the local effects of the point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector dir = ray.getDir();
        Vector normal = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(normal.dotProduct(dir));
        if (nv == 0) {
            return Color.BLACK;
        }
        int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector dirLight = lightSource.getL(intersection.point);
            double nl = alignZero(normal.dotProduct(dirLight));
            /* If the light is behind the point, ignore it. */
            if (nl * nv > 0) {
                if (unshaded(lightSource, dirLight, normal, intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, dirLight, normal, lightIntensity),
                                      calcSpecular(ks, dirLight, normal, dir, nShininess,
                                                   lightIntensity));
                }
            }
        }
        return color;
    }

    private Color calcSpecular(Double3 ks, Vector dirLight, Vector normal, Vector dir,
                               int nShininess, Color lightIntensity) {
        Vector reflectedDir = dirLight.add(normal.scale(-2 * dirLight.dotProduct(normal)));
        double t = alignZero(-reflectedDir.dotProduct(dir));
        t = alignZero(t);
        return t > 0 ? lightIntensity.scale(ks.scale(Math.pow(t, nShininess))) : Color.BLACK;
    }

    private Color calcDiffusive(Double3 kd, Vector dirLight, Vector normal, Color lightIntensity) {
        double s = alignZero(dirLight.dotProduct(normal));
        if (s < 0) {
            s *= -1;
        }
        return lightIntensity.scale(kd.scale(s));
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectPoints;
        GeoPoint closestPoint;

        intersectPoints = scene.geometries.findGeoIntersections(ray);
        if (intersectPoints == null) {
            return scene.background;
        }

        closestPoint = ray.findClosestGeoPoint(intersectPoints);
        return calcColor(closestPoint, ray);
    }

    /**
     * Finding Light Beam Cutting - Finding Shadow.
     * @param light The source of light
     * @param dirLight Direction of light
     * @param normal The normal vector
     * @param geopoint GeoPoint
     * @return Whether intersections were found or not (whether there is a shadow or not)
     */
    private boolean unshaded(LightSource light, Vector dirLight, Vector normal, GeoPoint geopoint) {
        Vector lightDirection = dirLight.scale(-1);
        Vector delta = normal.scale(normal.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = geopoint.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) {
            return true;
        }
        double lightDistance = light.getDistance(geopoint.point);
        /* Check if the point is after the light */
        return !(intersections.stream().allMatch(
            geoPoint -> alignZero(geoPoint.point.distance(geoPoint.point) - lightDistance) <= 0));
    }
}
