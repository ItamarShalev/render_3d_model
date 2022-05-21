package com.renderer;

import com.primitives.Color;
import com.primitives.Ray;
import com.scene.Scene;
import java.util.List;
import java.util.Optional;

/**
 * Class to handle the trace after ray.
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructor.
     * @param scene the scene
     * @throws NullPointerException if scene is null
     */
    public RayTracerBase(Scene scene) throws NullPointerException {
        this.scene = Optional
            .ofNullable(scene)
            .orElseThrow(() -> new NullPointerException("ERROR: scene can't be null"));
    }

    /**
     * Got a ray and return the color of the intersect geometries.
     * @param ray the ray
     * @return the color of the intersect geometries
     */
    public abstract Color traceRay(List<Ray> ray);
}
