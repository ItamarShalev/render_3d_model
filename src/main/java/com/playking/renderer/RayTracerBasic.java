package com.playking.renderer;

import com.playking.geometries.Intersect.GeoPoint;
import com.playking.primitives.Color;
import com.playking.primitives.Ray;
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
     * @param geoPoint the geoPoint to calculate the color
     * @return the color of the point
     */
    private Color calcColor(GeoPoint geoPoint) {
        return geoPoint.geometry.getEmission();
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectPoints;
        GeoPoint closestPoint;
        Color resultColor;

        intersectPoints = scene.geometries.findGeoIntersections(ray);
        if (intersectPoints == null) {
            resultColor = scene.background;
        } else {
            closestPoint = ray.findClosestGeoPoint(intersectPoints);
            resultColor = calcColor(closestPoint);
        }
        return resultColor;
    }
}
