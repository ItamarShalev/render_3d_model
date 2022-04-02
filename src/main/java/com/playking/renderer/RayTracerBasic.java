package com.playking.renderer;

import static com.playking.primitives.Util.alignZero;

import com.playking.geometries.Intersect.GeoPoint;
import com.playking.lighting.LightSource;
import com.playking.primitives.Color;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import com.playking.scene.Scene;
import java.util.List;

/**
 * Implement RayTracerBase to handle all the ray trace.
 */
public class RayTracerBasic extends RayTracerBase {
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
        double kd = intersection.geometry.getMaterial().kD;
        double ks = intersection.geometry.getMaterial().kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector dirLight = lightSource.getL(intersection.point);
            double nl = alignZero(normal.dotProduct(dirLight));
            /* If the light is behind the point, ignore it. */
            if (nl * nv > 0) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, dirLight, normal, lightIntensity),
                                  calcSpecular(ks, dirLight, normal, dir, nShininess,
                                               lightIntensity));
            }
        }
        return color;
    }

    private Color calcSpecular(double ks, Vector dirLight, Vector normal, Vector dir,
                               int nShininess, Color lightIntensity) {
        Vector reflectedDir = dirLight.add(normal.scale(-2 * dirLight.dotProduct(normal)));
        double t = alignZero(-reflectedDir.dotProduct(dir));
        t = alignZero(t);
        return t > 0 ? lightIntensity.scale(ks * Math.pow(t, nShininess)) : Color.BLACK;
    }

    private Color calcDiffusive(double kd, Vector dirLight, Vector normal, Color lightIntensity) {
        double s = alignZero(dirLight.dotProduct(normal));
        if (s < 0) {
            s *= -1;
        }
        return lightIntensity.scale(kd * s);
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
}
