package com.playking.renderer;

import static com.playking.primitives.Util.alignZero;

import com.playking.geometries.Intersect.GeoPoint;
import com.playking.lighting.LightSource;
import com.playking.primitives.Color;
import com.playking.primitives.Material;
import com.playking.primitives.Point;
import com.playking.primitives.Ray;
import com.playking.primitives.Vector;
import com.playking.scene.Scene;
import java.util.List;
import java.util.function.Function;

/**
 * Implement RayTracerBase to handle all the ray trace.
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final int MAX_CALC_COLOR_LEVEL = 10;

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
        return scene.ambientLight
            .getIntensity()
            .add(intersection.geometry.getEmission(), calcLocalEffects(intersection, ray));
    }

    /**
     * Calculate the local effects of the point.
     * @param intersection the geoPoint to calculate the local effects
     * @param ray the ray to calculate the local effects
     * @return the local effects of the point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        boolean isBehindThePoint;
        Color color = Color.BLACK;
        Vector dir = ray.getDir();
        Vector normal = intersection.geometry.getNormal(intersection.point);
        Material material = intersection.geometry.getMaterial();
        double nv = alignZero(normal.dotProduct(dir));
        if (nv == 0) {
            return color;
        }

        for (LightSource lightSource : scene.lights) {
            Vector dirLight = lightSource.getL(intersection.point);
            double nl = alignZero(normal.dotProduct(dirLight));
            isBehindThePoint = nl * nv > 0;
            if (isBehindThePoint && unshaded(lightSource, dirLight, normal, intersection)) {
                Color intensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(material, dirLight, normal, intensity),
                                  calcSpecular(material, dirLight, normal, dir, intensity));
            }
        }
        return color;
    }

    private Color calcSpecular(Material material, Vector dirLight, Vector normal, Vector dir,
                               Color lightIntensity) {
        Vector reflectedDir = dirLight.add(normal.scale(-2 * dirLight.dotProduct(normal)));
        double t = alignZero(-reflectedDir.dotProduct(dir));
        return t > 0 ? lightIntensity.scale(material.kS.scale(Math.pow(t, material.nShininess)))
                     : Color.BLACK;
    }

    private Color calcDiffusive(Material material, Vector dirLight, Vector normal,
                                Color lightIntensity) {
        double s = Math.abs(alignZero(dirLight.dotProduct(normal)));
        return lightIntensity.scale(material.kD.scale(s));
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
     * @param geoPoint GeoPoint
     * @return Whether intersections were found or not (whether there is a shadow or not)
     */
    private boolean unshaded(LightSource light, Vector dirLight, Vector normal, GeoPoint geoPoint) {
        Vector lightDirection = dirLight.scale(-1);
        Vector delta = normal.scale(normal.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = geoPoint.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) {
            return true;
        }
        double lightDistance = light.getDistance(geoPoint.point);
        Function<GeoPoint, Boolean> isPointAfterLight = geometryPoint -> alignZero(
            geometryPoint.point.distance(geometryPoint.point) - lightDistance) <= 0;

        return intersections.stream().noneMatch(isPointAfterLight::apply);
    }
}
