package com.playking.renderer;

import com.playking.primitives.Color;
import com.playking.primitives.Point;
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
     * @param point the point to calculate the color
     * @return the color of the point
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersectPoints;
        Point closestPoint;
        Color resultColor;

        intersectPoints = scene.geometries.findIntersections(ray);
        if (intersectPoints == null) {
            resultColor = scene.background;
        } else {
            closestPoint = ray.findClosestPoint(intersectPoints);
            resultColor = calcColor(closestPoint);
        }
        return resultColor;
    }
}
