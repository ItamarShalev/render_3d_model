package com.renderer;

import static com.primitives.Util.alignZero;

import com.geometries.Intersect.GeoPoint;
import com.lighting.LightSource;
import com.primitives.Color;
import com.primitives.Double3;
import com.primitives.Material;
import com.primitives.Point;
import com.primitives.Ray;
import com.primitives.Vector;
import com.scene.Scene;
import java.util.LinkedList;
import java.util.List;

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
     * Calculate the local effects of the point.
     * @param intersection the geoPoint to calculate the local effects
     * @param ray the ray to calculate the local effects
     * @param k max level of color
     * @return the local effects of the point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
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
            Double3 ktr = transparency(lightSource, dirLight, normal, intersection);
            if (isBehindThePoint) {
                if (!k.product(ktr).lowerThan(MIN_CALC_COLOR_K)) {
                    Color intensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(material, dirLight, normal, intensity),
                                      calcSpecular(material, dirLight, normal, dir, intensity));
                }
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
    public Color traceRay(List<Ray> rays) {
        List<Color> colors = new LinkedList<>();
        Color color;
        List<GeoPoint> intersectPoints;
        GeoPoint closestPoint;

        for (Ray ray : rays) {
            intersectPoints = scene.geometries.findGeoIntersections(ray);
            if (intersectPoints == null) {
                color = scene.background;
            } else {
                closestPoint = ray.findClosestGeoPoint(intersectPoints);
                color = calcColor(closestPoint, ray);
            }
            colors.add(color);
        }

        return Color.average(colors);
    }

    /**
     * Finding Light Beam Cutting - Finding Shadow.
     * @param light The source of light
     * @param dirLight Direction of light
     * @param normal The normal vector
     * @param geoPoint GeoPoint
     * @return Whether intersections were found or not (whether there is a shadow or not)
     */
    private Double3 transparency(LightSource light, Vector dirLight, Vector normal,
                                 GeoPoint geoPoint) {
        Vector lightDirection = dirLight.scale(-1);
        Vector delta = normal.scale(normal.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = geoPoint.point.add(delta);
        Ray lightRay = new Ray(geoPoint.point, lightDirection, normal);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        Double3 result = Double3.ONE;
        if (intersections == null) {
            return result;
        }

        double lightDistance = light.getDistance(point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                result = gp.geometry.getMaterial().kT.product(result);
                if (result.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO;
                }
            }
        }
        return result;
    }

    /**
     * Calculate the color of the scene.
     * @param intersection geo point intersections with the ray
     * @param level of the recursion
     * @param ray ray
     * @param k factor of the max value of the level
     * @return color of the geometry
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        if (intersection == null) {
            return scene.background;
        }
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculate the color of some point.
     * @param intersection the geoPoint to calculate the color
     * @param ray the ray to calculate the color
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return calcColor(intersection,
                         ray,
                         MAX_CALC_COLOR_LEVEL,
                         new Double3(DELTA)).add(scene.ambientLight.getIntensity());
    }

    /**
     * Construct a reflected ray from the geometry.
     * @param normal normal vector of the point on the geometry
     * @param point on the geometry
     * @param ray from the geometry
     * @return new reflected ray
     */
    private Ray constructReflectedRay(Vector normal, Point point, Ray ray) {
        Vector dir = ray.getDir();
        Vector normalDir = normal.scale(-2 * dir.dotProduct(normal));
        Vector result = dir.add(normalDir);
        /* use the constructor with 3 arguments to move the head */
        return new Ray(point, result, normal);
    }

    /**
     * Calculate the global effects of the scene.
     * @param geoPoint point on the geometry
     * @param ray from the geometry
     * @param level of recursion
     * @param k max value level
     * @return color
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        Double3 kR = material.kR.product(k);
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
        if (!kR.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(normal, geoPoint.point, ray);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            color = color.add(calcColor(reflectedPoint,
                                        reflectedRay,
                                        level - 1,
                                        kR).scale(material.kR));
        }
        Double3 kT = material.kT.product(k);


        if (!kT.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(normal, geoPoint.point, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            color = color.add(calcColor(refractedPoint,
                                        refractedRay,
                                        level - 1,
                                        kT).scale(material.kT));
        }
        return color;
    }

    /**
     * Construct the refracted ray of the point on the geometry.
     * @param normal normal vector
     * @param point on the geometry
     * @param ray from the geometry
     * @return new ray
     */
    private Ray constructRefractedRay(Vector normal, Point point, Ray ray) {
        return new Ray(point, ray.getDir(), normal);
    }

    /**
     * Find the closest intersection point of the ray with the geometry.
     * @param ray on the geometry
     * @return the closest geo point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        boolean isNullOrEmpty = intersections == null || intersections.isEmpty();
        return isNullOrEmpty ? null : ray.findClosestGeoPoint(intersections);
    }
}
